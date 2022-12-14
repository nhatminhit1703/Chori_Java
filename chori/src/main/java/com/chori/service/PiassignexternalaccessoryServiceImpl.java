package com.chori.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chori.AbstractDao;
import com.chori.AbstractServiceImpl;
import com.chori.dao.AccessoryDao;
import com.chori.dao.ColorDao;
import com.chori.dao.FpiDao;
import com.chori.dao.FpidetailDao;
import com.chori.dao.GarmentstyleDao;
import com.chori.dao.GarmentstyleaccessorydetailDao;
import com.chori.dao.PIDao;
import com.chori.dao.PiassignexternalaccessoryDao;
import com.chori.dao.PigriddetailDao;
import com.chori.dao.RfpiDao;
import com.chori.dao.RfpidetailDao;
import com.chori.dao.UserDao;
import com.chori.entity.Fpi;
import com.chori.entity.Garmentstyleaccessorydetail;
import com.chori.entity.Pi;
import com.chori.entity.Piassignexternalaccessory;
import com.chori.entity.Pigriddetail;
import com.chori.entity.Rfpi;
import com.chori.model.ColorForPiModel;
import com.chori.model.PiassignexternalaccessoryModel;
import com.chori.model.TotalAccForAssignExternalModel;

@Service("piassignexternalaccessoryService")
public class PiassignexternalaccessoryServiceImpl extends
		AbstractServiceImpl<Piassignexternalaccessory, Integer> implements
		PiassignexternalaccessoryService {
	private PiassignexternalaccessoryDao dao;

	@Autowired
	private PIDao piDao;

	@Autowired
	private PigriddetailDao pigriddetailDao;

	@Autowired
	private GarmentstyleaccessorydetailDao garmentstyleaccessorydetailDao;

	@Autowired
	private AccessoryDao accessoryDao;

	@Autowired
	private ColorDao colorDao;

	@Autowired
	private GarmentstyleDao garmentStyleDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FpidetailDao fpiDetailDao;
	
	@Autowired
	private FpiDao fpiDao;
	
	@Autowired
	private RfpiDao rfpiDao;
	
	@Autowired
	private RfpidetailDao rfpidetailDao;

	@Autowired
	public PiassignexternalaccessoryServiceImpl(
			@Qualifier("piassignexternalaccessoryDao") AbstractDao<Piassignexternalaccessory, Integer> abstractDao) {
		super(abstractDao);
		this.dao = (PiassignexternalaccessoryDao) abstractDao;
	}

	// ????a list lstPiassignexternalaccessoryModel ra ngo??i ????? l??c nh???n assign ?????
	// add th?? id c???a t???ng PiassignexternalaccessoryModel tr??ng
	List<PiassignexternalaccessoryModel> lstPiassignexternalaccessoryModel = new ArrayList<PiassignexternalaccessoryModel>();

	/**
	 * This function is used to get List PiassignexternalaccessoryModel When
	 * Press Assign (data not in database now) H??m x??? l?? l??c hi???n dialog, kh???i
	 * t???o c??c Piassignexternalaccessory l???n ?????u, ch??a l??u v??o db
	 */
	public List<PiassignexternalaccessoryModel> getListPiassignexternalaccessoryModelWhenPressAssign(
			String lotnumber) {
		log.info(String.format("getAllGarmentstyle in class: %s", getClass()));
		try {
			List<Garmentstyleaccessorydetail> lstGarmentstyleaccessorydetail;
			PiassignexternalaccessoryModel piassignexternalaccessoryModel;
			// List<PiassignexternalaccessoryModel>
			// lstPiassignexternalaccessoryModel= new
			// ArrayList<PiassignexternalaccessoryModel>();
			lstPiassignexternalaccessoryModel.clear();

			// l???y piGridDetail qua lot no
			int pigridCode = piDao.findById(lotnumber).getPigrid()
					.getPigridcode();
			List<Pigriddetail> lstGridDetail = pigriddetailDao
					.getListPigriddetailByPigridcode(pigridCode);

			// v?? l?? l???n ?????u add n??n l??u c??c id c???a
			// PiassignexternalaccessoryModel theo th??? t??? t??ng d???n, ????? l??t bi???t
			// n??o add n??o ko
			int i = 1;
			
			//m??ng x??? l?? display name
			String[] splitGarmentStyleCode;

			// l???p qua grid detail
			for (Pigriddetail pigriddetail : lstGridDetail) {
				// l???y ra c??c Garmentstyleaccessorydetail qua garmentStyleCode,
				// size
				lstGarmentstyleaccessorydetail = garmentstyleaccessorydetailDao
						.getListGarmentstyleaccessorydetailByGarmentStyleNameAndSize(
								pigriddetail.getGarmentstyle()
										.getGarmentstylecode(), pigriddetail
										.getSize().getSizecode());
				for (Garmentstyleaccessorydetail garmentstyleaccessorydetail : lstGarmentstyleaccessorydetail) {
					// n???u accessory l?? External th?? m???i x??t ti???p
					if (garmentstyleaccessorydetail.getAccessory().getKind()
							.equals("External")) {
						// t???o ra pi assign external accessory
						piassignexternalaccessoryModel = new PiassignexternalaccessoryModel();
						// set id c???a t???ng PiassignexternalaccessoryModel t???i
						// ????y
						piassignexternalaccessoryModel
								.setPiassignexternalaccessorycode(i++);

						piassignexternalaccessoryModel
								.setAccessoryCode(garmentstyleaccessorydetail
										.getAccessory().getAccessorycode());
						piassignexternalaccessoryModel
								.setAccessoryName(garmentstyleaccessorydetail
										.getAccessory().getName());
						piassignexternalaccessoryModel
								.setColorcode(pigriddetail.getColor()
										.getColorcode());
						piassignexternalaccessoryModel
								.setColorName(pigriddetail.getColor()
										.getDescription());
						piassignexternalaccessoryModel.setSizecode(pigriddetail
								.getSize().getSizecode());
						piassignexternalaccessoryModel.setSizename(pigriddetail
								.getSize().getSizename());
						piassignexternalaccessoryModel
								.setGarmentstylecode(pigriddetail
										.getGarmentstyle()
										.getGarmentstylecode());
						piassignexternalaccessoryModel
								.setGarmentstyleName(pigriddetail
										.getGarmentstyle().getDescription());
						piassignexternalaccessoryModel.setLotnumber(lotnumber);
						piassignexternalaccessoryModel
								.setPigriddetail(pigriddetail.getPigriddetail());
						piassignexternalaccessoryModel.setCreator("admin");
						piassignexternalaccessoryModel
								.setCreatedate(new Date());
						piassignexternalaccessoryModel
								.setEstimateqty(pigriddetail.getFavalue()
										* garmentstyleaccessorydetail
												.getUsedvalue());
						piassignexternalaccessoryModel
								.setMode(garmentstyleaccessorydetail
										.getAccessory().getMode());
						piassignexternalaccessoryModel
								.setImgurl(garmentstyleaccessorydetail
										.getAccessory().getImgurl1());

						piassignexternalaccessoryModel
								.setGarmentstyleaccessorydetailcode(garmentstyleaccessorydetail
										.getGarmentstyleaccessorydetailcode());
						//set garment style display name
						splitGarmentStyleCode = pigriddetail
								.getGarmentstyle()
								.getGarmentstylecode().split("@@@");
						piassignexternalaccessoryModel.setGarmentStyleDisplayName(splitGarmentStyleCode[0]);

						lstPiassignexternalaccessoryModel
								.add(piassignexternalaccessoryModel);
					}
				}
			}

			Collections.sort(lstPiassignexternalaccessoryModel,
					PiassignexternalaccessoryModel.accessoryNameComparator);

			return lstPiassignexternalaccessoryModel;
		} catch (Exception e) {
			log.error(String.format(
					"getAllGarmentstyle in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to add list PiAssignExternalAccessory for the 1st
	 * time H??m assign external accessory cho PI l???n ?????u ti??n
	 * 
	 * @param lstNotAssign
	 * @param creator
	 * @return
	 */
	public boolean add1stTimePiAssignExternalAccessory(
			ArrayList<PiassignexternalaccessoryModel> lstNotAssign,
			String creator) {
		log.info(String.format(
				"add1stTimePiAssignExternalAccessory in class: %s", getClass()));
		try {
			// l???p qua list chung
			for (PiassignexternalaccessoryModel piassignexternalaccessoryModel : lstPiassignexternalaccessoryModel) {
				// l???p qua list ko assign
				for (PiassignexternalaccessoryModel piassignexternalaccessoryNotAssign : lstNotAssign) {
					// n???u id c???a list chung = id c???a list ko assign th?? set
					// estimate l?? o
					if (piassignexternalaccessoryModel
							.getPiassignexternalaccessorycode() == piassignexternalaccessoryNotAssign
							.getPiassignexternalaccessorycode()) {
						piassignexternalaccessoryModel
								.setEstimateqty((float) 0);
					}
				}
			}

			Piassignexternalaccessory piassignexternalaccessory;

			// l???p qua list chung r???i th??m v?? db
			for (PiassignexternalaccessoryModel piassignexternalaccessoryModel : lstPiassignexternalaccessoryModel) {
				piassignexternalaccessory = new Piassignexternalaccessory();
				piassignexternalaccessory.setAccessory(accessoryDao
						.findById(piassignexternalaccessoryModel
								.getAccessoryCode()));
				piassignexternalaccessory
						.setColor(colorDao
								.findById(piassignexternalaccessoryModel
										.getColorcode()));
				piassignexternalaccessory.setGarmentstyle(garmentStyleDao
						.findById(piassignexternalaccessoryModel
								.getGarmentstylecode()));
				piassignexternalaccessory
						.setPi(piDao.findById(piassignexternalaccessoryModel
								.getLotnumber()));
				piassignexternalaccessory.setPigriddetail(pigriddetailDao
						.findById(piassignexternalaccessoryModel
								.getPigriddetail()));
				piassignexternalaccessory.setUser(userDao.findById("admin"));
				piassignexternalaccessory
						.setEstimateqty(piassignexternalaccessoryModel
								.getEstimateqty());
				piassignexternalaccessory.setCreatedate(new Date());
				piassignexternalaccessory
						.setGarmentstyleaccessorydetail(garmentstyleaccessorydetailDao
								.findById(piassignexternalaccessoryModel
										.getGarmentstyleaccessorydetailcode()));

				dao.save(piassignexternalaccessory);
			}

			return true;
		} catch (Exception e) {
			log.error(String
					.format("add1stTimePiAssignExternalAccessory in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to check if a PI with lot No is already assign
	 * external accessory H??m ki???m tra xem pi vs lotNo x??c ?????nh ???? ??c assign
	 * external accesory ch??a?
	 * 
	 * @param lotNumber
	 * @return
	 */
	public boolean isPiAssignedExternalAccessory(String lotNumber) {
		log.debug(String.format("isPiAssignedExternalAccessory in class: %s",
				getClass()));
		try {
			return dao.isPiAssignedExternalAccessory(lotNumber);
		} catch (RuntimeException re) {
			log.error(String.format(
					"isPiAssignedExternalAccessory in class %s has error: %s",
					getClass(), re.getMessage()));
			throw re;
		}
	}

	/**
	 * This function is used to get list Pi assign external accessory By Lot
	 * Number to show table after assign H??m ??c d??ng l???y pi assign external ?????
	 * show ra view sau khi assign
	 * 
	 * @param lotNumber
	 * @return
	 */
	public List<PiassignexternalaccessoryModel> getListPiassignexternalaccessoryByLotNumber(
			String lotNumber) {
		log.debug(String.format(
				"getListPiassignexternalaccessoryByLotNumber in class: %s",
				getClass()));
		try {
			// list k???t qu??? tr??? v???
			List<PiassignexternalaccessoryModel> lstResult = new ArrayList<PiassignexternalaccessoryModel>();
			PiassignexternalaccessoryModel piassignexternalaccessoryModel;

			//l???y ra pi = lot number (????? l???y factory)
			Pi pi = piDao.findById(lotNumber);
//			List<Garmentstyleaccessorydetail> lstGarmentstyleaccessorydetail;
			Set<String> setAccessoryName = new HashSet<String>();
			
			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumber(lotNumber);
			
			//m??ng x??? l?? display name
			String[] splitGarmentStyleCode;
			
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
				// if estimate m?? >0 th?? m???i add v?? list
				if (piassignexternalaccessory.getEstimateqty() > 0) {
					piassignexternalaccessoryModel = new PiassignexternalaccessoryModel();
					piassignexternalaccessoryModel
							.setPiassignexternalaccessorycode(piassignexternalaccessory
									.getPiassignexternalaccessorycode());
					piassignexternalaccessoryModel
							.setAccessoryCode(piassignexternalaccessory
									.getAccessory().getAccessorycode());
					piassignexternalaccessoryModel
							.setAccessoryName(piassignexternalaccessory
									.getAccessory().getName());
					piassignexternalaccessoryModel
							.setImgurl(piassignexternalaccessory.getAccessory()
									.getImgurl1());
					piassignexternalaccessoryModel
							.setGarmentstylecode(piassignexternalaccessory
									.getGarmentstyle().getGarmentstylecode());
					piassignexternalaccessoryModel
							.setGarmentstyleName(piassignexternalaccessory
									.getGarmentstyle().getDescription());
					piassignexternalaccessoryModel
							.setColorcode(piassignexternalaccessory.getColor()
									.getColorcode());
					piassignexternalaccessoryModel
							.setColorName(piassignexternalaccessory.getColor()
									.getDescription());
					piassignexternalaccessoryModel
							.setSizecode(piassignexternalaccessory
									.getPigriddetail().getSize().getSizecode());
					piassignexternalaccessoryModel
							.setSizename(piassignexternalaccessory
									.getPigriddetail().getSize().getSizename());
					piassignexternalaccessoryModel
							.setEstimateqty(piassignexternalaccessory
									.getEstimateqty());
					piassignexternalaccessoryModel
							.setMode(piassignexternalaccessory.getAccessory()
									.getMode());
					
					splitGarmentStyleCode = piassignexternalaccessory.getGarmentstyle().getGarmentstylecode().split("@@@");
					piassignexternalaccessoryModel.setGarmentStyleDisplayName(splitGarmentStyleCode[0]);
					
					//set factory part (l???y factory t??? lot number c???a pi)
					piassignexternalaccessoryModel.setFactorycode(pi.getFactory().getFactorycode());
					piassignexternalaccessoryModel.setFactoryShortname(pi.getFactory().getShortname());
					
					//set accessory supplier
					//n???u order external m?? kh??c null th?? set Accsupplier
					if(piassignexternalaccessory
									.getOrderexternalaccessory() != null){
						piassignexternalaccessoryModel
						.setAccsuppliercode(piassignexternalaccessory
								.getOrderexternalaccessory()
								.getAccessorysupplier()
								.getAccsuppliercode());
						piassignexternalaccessoryModel.setAccsupplierShortname(piassignexternalaccessory
								.getOrderexternalaccessory()
								.getAccessorysupplier().getShortname());
						//
						piassignexternalaccessoryModel.setOrderQty(piassignexternalaccessory
								.getOrderexternalaccessory().getOrderquantity());
						piassignexternalaccessoryModel.setOrderDate(piassignexternalaccessory
								.getOrderexternalaccessory().getOrderdate());
						piassignexternalaccessoryModel.setEstimateDeliveryDate(piassignexternalaccessory
								.getOrderexternalaccessory().getEstimatedelvdate());
						piassignexternalaccessoryModel.setActualQty(piassignexternalaccessory
								.getOrderexternalaccessory().getActualdelvquantity());
						piassignexternalaccessoryModel.setStatus(piassignexternalaccessory
								.getOrderexternalaccessory().getStatus());
						piassignexternalaccessoryModel.setPaymentStatus(piassignexternalaccessory
								.getOrderexternalaccessory().getPaymentstatus());
						
						piassignexternalaccessoryModel.setOrderSheetNoAndStatus(piassignexternalaccessory
								.getOrderexternalaccessory().getOrdersheetno()+"/Status: "+piassignexternalaccessory
								.getOrderexternalaccessory().getStatus()+"/Payment status: "+piassignexternalaccessory
								.getOrderexternalaccessory().getPaymentstatus());
					}
					
					//t??nh gi?? tr??? estimate cho fpi (ki???m tra xem c?? import fpi ch??a)
					Fpi fpi = fpiDao.getFpiHasBiggestVersionByLotNumber(lotNumber);
					if(fpi!=null){
						//ki???m tra ???? import fpi, n???u fpi grid detail size m??> 0 th?? import r???i
						if(fpiDetailDao.getListFpidetailByLotNumberAndVersion(lotNumber, fpi.getVersion()).size()>0){
							//l???y pi gridDetail
							Pigriddetail pigriddetail = piassignexternalaccessory.getPigriddetail();
							
							int fpiValue = fpiDetailDao.getFpiValueOfFpidetailByFpidetailcodeColorCodeGarmentStyleCodeSizeCode(fpi.getFpicode(),pigriddetail.getColor().getColorcode(),pigriddetail.getGarmentstyle().getGarmentstylecode(),pigriddetail.getSize().getSizecode());
							
							piassignexternalaccessoryModel
							.setEstimateFpiQty(fpiValue
									* piassignexternalaccessory
											.getGarmentstyleaccessorydetail().getUsedvalue());
						}else{
							//ko th?? cho fpi l?? 0
							piassignexternalaccessoryModel.setEstimateFpiQty((float) 0);
						}
					}else{
						//ko th?? cho fpi l?? 0
						piassignexternalaccessoryModel.setEstimateFpiQty((float) 0);
					}
					

					//t??nh gi?? tr??? estimate cho rfpi
					Rfpi rfpi = rfpiDao.getRfpiHasBiggestVersionByLotNumber(lotNumber);
					if(rfpi!=null){
						//ki???m tra ???? import fpi, n???u fpi grid detail size m??> 0 th?? import r???i
						if(rfpidetailDao.getListRfpidetailByLotNumberAndVersion(lotNumber, rfpi.getVersion()).size()>0){
							//l???y pi gridDetail
							Pigriddetail pigriddetail = piassignexternalaccessory.getPigriddetail();
							
							int rfpiValue = rfpidetailDao.getRfpiValueOfRfpidetailByRfpidetailcodeColorCodeGarmentStyleCodeSizeCode(rfpi.getRfpigrid(),pigriddetail.getColor().getColorcode(),pigriddetail.getGarmentstyle().getGarmentstylecode(),pigriddetail.getSize().getSizecode());
							
							piassignexternalaccessoryModel
							.setEstimateRfpiQty(rfpiValue
									* piassignexternalaccessory
											.getGarmentstyleaccessorydetail().getUsedvalue());
						}else{
							//ko th?? cho rfpi l?? 0
							piassignexternalaccessoryModel.setEstimateRfpiQty((float) 0);
						}
					}else{
						//ko th?? cho rfpi l?? 0
						piassignexternalaccessoryModel.setEstimateRfpiQty((float) 0);
					}

					lstResult.add(piassignexternalaccessoryModel);
					
					//t??m t???t c??? c??c accessory ??c assign
					//n???u ch??a c?? th?? th??m v?? set
					if(!setAccessoryName.contains(piassignexternalaccessory.getAccessory().getAccessorycode())){
						setAccessoryName.add(piassignexternalaccessory.getAccessory().getAccessorycode());
					}
				}
			}

//			for (String string : setAccessoryName) {
//				System.err.println(string);
//			}
			
			//l???p qua set color, g??n code v?? gi?? tr??? cho t???ng m??u
			List<TotalAccForAssignExternalModel> lstTotalAccForAssignExternalModel = new ArrayList<TotalAccForAssignExternalModel>();
			TotalAccForAssignExternalModel totalAccForAssignExternalModelTmp;
			for (String string : setAccessoryName) {
				totalAccForAssignExternalModelTmp= new TotalAccForAssignExternalModel();
				totalAccForAssignExternalModelTmp.setAccessoryCode(string);
				totalAccForAssignExternalModelTmp.setEstimateqty((float) 0);
				totalAccForAssignExternalModelTmp.setEstimateFpiQty((float) 0);
				totalAccForAssignExternalModelTmp.setEstimateRFpiQty((float) 0);
				lstTotalAccForAssignExternalModel.add(totalAccForAssignExternalModelTmp);
			}
			
			//
			float sumEstimateqty = 0;
			float sumEstimateFpiQty = 0;
			float sumEstimateRfpiQty = 0;
			for (PiassignexternalaccessoryModel piassignexternalaccessoryModel1 : lstResult) {
				for (TotalAccForAssignExternalModel totalAccForAssignExternalModel : lstTotalAccForAssignExternalModel) {
					if(totalAccForAssignExternalModel.getAccessoryCode().equals(piassignexternalaccessoryModel1.getAccessoryCode())){
						sumEstimateqty = totalAccForAssignExternalModel.getEstimateqty() + piassignexternalaccessoryModel1.getEstimateqty();
						totalAccForAssignExternalModel.setEstimateqty(sumEstimateqty);
						//t??nh t???ng fpi
						sumEstimateFpiQty = totalAccForAssignExternalModel.getEstimateFpiQty() + piassignexternalaccessoryModel1.getEstimateFpiQty();
						totalAccForAssignExternalModel.setEstimateFpiQty(sumEstimateFpiQty);
						//t??nh t???ng rfpi
						sumEstimateRfpiQty = totalAccForAssignExternalModel.getEstimateRFpiQty() + piassignexternalaccessoryModel1.getEstimateRfpiQty();
						totalAccForAssignExternalModel.setEstimateRFpiQty(sumEstimateRfpiQty);
					}
				}
			}
			
			//l???p qua list k???t qu???, l???p qua list acc, set total t????ng ???ng
			for (PiassignexternalaccessoryModel piassignexternalaccessoryModel1 : lstResult) {
				for (TotalAccForAssignExternalModel totalAccForAssignExternalModel : lstTotalAccForAssignExternalModel) {
					if(totalAccForAssignExternalModel.getAccessoryCode().equals(piassignexternalaccessoryModel1.getAccessoryCode())){
						piassignexternalaccessoryModel1.setTotalEstimateByAccessory(totalAccForAssignExternalModel.getEstimateqty());
						piassignexternalaccessoryModel1.setTotalEstimateFpiByAccessory(totalAccForAssignExternalModel.getEstimateFpiQty());
						piassignexternalaccessoryModel1.setTotalEstimateRfpiByAccessory(totalAccForAssignExternalModel.getEstimateRFpiQty());
					}
				}
			}
			
			for (TotalAccForAssignExternalModel totalAccForAssignExternalModel : lstTotalAccForAssignExternalModel) {
				System.err.println(totalAccForAssignExternalModel);
			}
			
			// sort theo accessory name
			Collections.sort(lstResult,
					PiassignexternalaccessoryModel.accessoryNameComparator);
			return lstResult;
		} catch (RuntimeException re) {
			log.error(String
					.format("getListPiassignexternalaccessoryByLotNumber in class %s has error: %s",
							getClass(), re.getMessage()));
			throw re;
		}
	}

	// List l??u nh???ng PiassignexternalaccessoryModel c?? th??? th??m m???i khi edit
	List<PiassignexternalaccessoryModel> lstAddNewWhenEdit = new ArrayList<PiassignexternalaccessoryModel>();

	/**
	 * This function is used to get list Pi assign external accessory By Lot
	 * Number to show into dialog to edit H??m ??c d??ng l???y pi assign external ?????
	 * show v??o dialog ????? edit
	 * 
	 * @param lotNumber
	 * @return
	 */
	public List<PiassignexternalaccessoryModel> getListPiassignexternalaccessoryForEditByLotNumber(
			String lotNumber) {
		log.debug(String.format(
				"getListPiassignexternalaccessoryByLotNumber in class: %s",
				getClass()));
		try {
			// list k???t qu??? tr??? v???
			List<PiassignexternalaccessoryModel> lstResult = new ArrayList<PiassignexternalaccessoryModel>();
			PiassignexternalaccessoryModel piassignexternalaccessoryModel;

			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumber(lotNumber);
			
			//m??ng x??? l?? display name
			String[] splitGarmentStyleCode;
			
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
				// if estimate m?? >0 th?? m???i add v?? list
				// if(piassignexternalaccessory.getEstimateqty()>0){
				piassignexternalaccessoryModel = new PiassignexternalaccessoryModel();
				// set id c???a t???ng PiassignexternalaccessoryModel t???i ????y
				piassignexternalaccessoryModel
						.setPiassignexternalaccessorycode(piassignexternalaccessory
								.getPiassignexternalaccessorycode());
				piassignexternalaccessoryModel
						.setAccessoryCode(piassignexternalaccessory
								.getAccessory().getAccessorycode());
				piassignexternalaccessoryModel
						.setAccessoryName(piassignexternalaccessory
								.getAccessory().getName());
				piassignexternalaccessoryModel
						.setColorcode(piassignexternalaccessory.getColor()
								.getColorcode());
				piassignexternalaccessoryModel
						.setColorName(piassignexternalaccessory.getColor()
								.getDescription());
				piassignexternalaccessoryModel
						.setSizecode(piassignexternalaccessory
								.getPigriddetail().getSize().getSizecode());
				piassignexternalaccessoryModel
						.setSizename(piassignexternalaccessory
								.getPigriddetail().getSize().getSizename());
				piassignexternalaccessoryModel
						.setGarmentstylecode(piassignexternalaccessory
								.getGarmentstyle().getGarmentstylecode());
				piassignexternalaccessoryModel
						.setGarmentstyleName(piassignexternalaccessory
								.getGarmentstyle().getDescription());
				piassignexternalaccessoryModel.setLotnumber(lotNumber);
				piassignexternalaccessoryModel
						.setPigriddetail(piassignexternalaccessory
								.getPigriddetail().getPigriddetail());
				piassignexternalaccessoryModel
						.setEstimateqty(piassignexternalaccessory
								.getEstimateqty());
				piassignexternalaccessoryModel
						.setMode(piassignexternalaccessory.getAccessory()
								.getMode());
				piassignexternalaccessoryModel
						.setImgurl(piassignexternalaccessory.getAccessory()
								.getImgurl1());
				
				//set garment style display name
				splitGarmentStyleCode = piassignexternalaccessory
						.getGarmentstyle()
						.getGarmentstylecode().split("@@@");
				piassignexternalaccessoryModel.setGarmentStyleDisplayName(splitGarmentStyleCode[0]);

				lstResult.add(piassignexternalaccessoryModel);
				// }
			}

			// ///////////////////////////
			lstAddNewWhenEdit.clear();
			List<PiassignexternalaccessoryModel> lstTotal = getListPiassignexternalaccessoryModelVsPigridDetail(lotNumber);
			List<PiassignexternalaccessoryModel> lstSaved = getListPiassignexternalaccessorySaved(lotNumber);

			boolean flag = false;

			for (PiassignexternalaccessoryModel piassignexternalaccessoryModel2 : lstTotal) {
				for (PiassignexternalaccessoryModel piassignexternalaccessoryModel3 : lstSaved) {
					// n???u m?? garmentStyleAccessoryDetail m?? tr??ng, => ???? t???n
					// t???i, set c??? = true
					if (piassignexternalaccessoryModel2
							.getGarmentstyleaccessorydetailcode() == piassignexternalaccessoryModel3
							.getGarmentstyleaccessorydetailcode()) {
						flag = true;
						break;
					}
				}
				// n???u m?? ch??a t???n t???i th?? th??m
				if (flag == false) {
					lstResult.add(piassignexternalaccessoryModel2);
					lstAddNewWhenEdit.add(piassignexternalaccessoryModel2);
				}
				flag = false;
			}
			// //////////////////////////

			// sort theo accessory name
			Collections.sort(lstResult,
					PiassignexternalaccessoryModel.accessoryNameComparator);
			return lstResult;
		} catch (RuntimeException re) {
			log.error(String
					.format("getListPiassignexternalaccessoryByLotNumber in class %s has error: %s",
							getClass(), re.getMessage()));
			throw re;
		}
	}

	/**
	 * This function is used to edit Pi Assign External Accessory | H??m d??ng ?????
	 * edit l???i assign external accessory xem ch???n acc n??o, b??? acc n??o
	 * 
	 * @param lstAssignOrNot
	 * @param modifier
	 * @return
	 */
	public boolean editPiAssignExternalAccessory(
			ArrayList<PiassignexternalaccessoryModel> lstAssignOrNot,
			String modifier) {
		log.info(String.format("editPiAssignExternalAccessory in class: %s",
				getClass()));
		try {
			// khai b??o 1 Piassignexternalaccessory ????? find by id
			Piassignexternalaccessory piassignexternalaccessory;
			List<Garmentstyleaccessorydetail> lstGarmentstyleaccessorydetail;

			for (PiassignexternalaccessoryModel piassignexternalaccessoryNotAssign : lstAssignOrNot) {
				// th??m ??i???u ki???n m???i l?? n???u Piassignexternalaccessorycode m??
				// =-1 th?? add
				if (piassignexternalaccessoryNotAssign
						.getPiassignexternalaccessorycode() < 0) {
					// n???u c?? t??ch v?? checkbox
					if (piassignexternalaccessoryNotAssign.isAssign() == true) {
						for (PiassignexternalaccessoryModel piassignexternalaccessoryModel : lstAddNewWhenEdit) {
							// n???u Piassignexternalaccessorycode = nhau th?? add
							if (piassignexternalaccessoryNotAssign
									.getPiassignexternalaccessorycode() == piassignexternalaccessoryModel
									.getPiassignexternalaccessorycode()) {
								// add
								piassignexternalaccessory = new Piassignexternalaccessory();
								piassignexternalaccessory
										.setAccessory(accessoryDao
												.findById(piassignexternalaccessoryModel
														.getAccessoryCode()));
								piassignexternalaccessory
										.setColor(colorDao
												.findById(piassignexternalaccessoryModel
														.getColorcode()));
								piassignexternalaccessory
										.setGarmentstyle(garmentStyleDao
												.findById(piassignexternalaccessoryModel
														.getGarmentstylecode()));
								piassignexternalaccessory
										.setPi(piDao
												.findById(piassignexternalaccessoryModel
														.getLotnumber()));
								piassignexternalaccessory
										.setPigriddetail(pigriddetailDao
												.findById(piassignexternalaccessoryModel
														.getPigriddetail()));
								piassignexternalaccessory.setUser(userDao
										.findById("admin"));
								piassignexternalaccessory
										.setEstimateqty(-piassignexternalaccessoryModel
												.getEstimateqty());
								piassignexternalaccessory
										.setCreatedate(new Date());
								piassignexternalaccessory
										.setGarmentstyleaccessorydetail(garmentstyleaccessorydetailDao
												.findById(piassignexternalaccessoryModel
														.getGarmentstyleaccessorydetailcode()));

								dao.save(piassignexternalaccessory);
								// end add
							}
						}
					}
				} else {// ko th?? l??m b.thg
						// find by id ra t???ng pi assign external accessory
					piassignexternalaccessory = dao
							.findById(piassignexternalaccessoryNotAssign
									.getPiassignexternalaccessorycode());
					// n???u c?? check th?? update l???i s??? l?????ng nh?? b??nh thg
					if (piassignexternalaccessoryNotAssign.isAssign() == true) {
						// l???y ra used value
						lstGarmentstyleaccessorydetail = garmentstyleaccessorydetailDao
								.getListGarmentstyleaccessorydetailByGarmentStyleCodeAccessoryCodeAndSize(
										piassignexternalaccessory
												.getGarmentstyle()
												.getGarmentstylecode(),
										piassignexternalaccessory
												.getAccessory()
												.getAccessorycode(),
										piassignexternalaccessory
												.getPigriddetail().getSize()
												.getSizecode());
						for (Garmentstyleaccessorydetail garmentstyleaccessorydetail : lstGarmentstyleaccessorydetail) {
							// update l???i s??? l?????ng accessory
							piassignexternalaccessory
									.setEstimateqty(piassignexternalaccessory
											.getPigriddetail().getFavalue()
											* garmentstyleaccessorydetail
													.getUsedvalue());
						}
						dao.update(piassignexternalaccessory);
						lstGarmentstyleaccessorydetail.clear();
					} else if (piassignexternalaccessoryNotAssign.isAssign() == false) {
						// n???u ko check th?? set s??? l?????ng pcs v??? 0
						piassignexternalaccessory.setEstimateqty((float) 0);
						dao.update(piassignexternalaccessory);
					}
				}
			}

			return true;
		} catch (Exception e) {
			log.error(String.format(
					"editPiAssignExternalAccessory in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * h??m l???y c??? m?? garmentStyleAccessoryDetail ???ng vs pigridDetail
	 */
	public List<PiassignexternalaccessoryModel> getListPiassignexternalaccessoryModelVsPigridDetail(
			String lotnumber) {
		log.info(String.format("getAllGarmentstyle in class: %s", getClass()));
		try {
			List<Garmentstyleaccessorydetail> lstGarmentstyleaccessorydetail;
			PiassignexternalaccessoryModel piassignexternalaccessoryModel;
			List<PiassignexternalaccessoryModel> lstResult = new ArrayList<PiassignexternalaccessoryModel>();
			// lstPiassignexternalaccessoryModel.clear();

			// l???y piGridDetail qua lot no
			int pigridCode = piDao.findById(lotnumber).getPigrid()
					.getPigridcode();
			List<Pigriddetail> lstGridDetail = pigriddetailDao
					.getListPigriddetailByPigridcode(pigridCode);

			// v?? l?? l???n ?????u add n??n l??u c??c id c???a
			// PiassignexternalaccessoryModel theo th??? t??? t??ng d???n, ????? l??t bi???t
			// n??o add n??o ko
			int i = -1;

			// l???p qua grid detail
			for (Pigriddetail pigriddetail : lstGridDetail) {
				// l???y ra c??c Garmentstyleaccessorydetail qua garmentStyleCode,
				// size
				lstGarmentstyleaccessorydetail = garmentstyleaccessorydetailDao
						.getListGarmentstyleaccessorydetailByGarmentStyleNameAndSize(
								pigriddetail.getGarmentstyle()
										.getGarmentstylecode(), pigriddetail
										.getSize().getSizecode());
				for (Garmentstyleaccessorydetail garmentstyleaccessorydetail : lstGarmentstyleaccessorydetail) {
					// n???u accessory l?? External th?? m???i x??t ti???p
					if (garmentstyleaccessorydetail.getAccessory().getKind()
							.equals("External")) {
						// t???o ra pi assign external accessory
						piassignexternalaccessoryModel = new PiassignexternalaccessoryModel();
						// set id c???a t???ng PiassignexternalaccessoryModel t???i
						// ????y
						piassignexternalaccessoryModel
								.setPiassignexternalaccessorycode(i--);

						piassignexternalaccessoryModel
								.setAccessoryCode(garmentstyleaccessorydetail
										.getAccessory().getAccessorycode());
						piassignexternalaccessoryModel
								.setAccessoryName(garmentstyleaccessorydetail
										.getAccessory().getName());
						piassignexternalaccessoryModel
								.setColorcode(pigriddetail.getColor()
										.getColorcode());
						piassignexternalaccessoryModel
								.setColorName(pigriddetail.getColor()
										.getDescription());
						piassignexternalaccessoryModel.setSizecode(pigriddetail
								.getSize().getSizecode());
						piassignexternalaccessoryModel.setSizename(pigriddetail
								.getSize().getSizename());
						piassignexternalaccessoryModel
								.setGarmentstylecode(pigriddetail
										.getGarmentstyle()
										.getGarmentstylecode());
						piassignexternalaccessoryModel
								.setGarmentstyleName(pigriddetail
										.getGarmentstyle().getDescription());
						piassignexternalaccessoryModel.setLotnumber(lotnumber);
						piassignexternalaccessoryModel
								.setPigriddetail(pigriddetail.getPigriddetail());
						piassignexternalaccessoryModel.setCreator("admin");
						piassignexternalaccessoryModel
								.setCreatedate(new Date());
						piassignexternalaccessoryModel.setEstimateqty(-1
								* pigriddetail.getFavalue()
								* garmentstyleaccessorydetail.getUsedvalue());
						// piassignexternalaccessoryModel.setEstimateqty((float)
						// 0);
						piassignexternalaccessoryModel
								.setMode(garmentstyleaccessorydetail
										.getAccessory().getMode());
						piassignexternalaccessoryModel
								.setImgurl(garmentstyleaccessorydetail
										.getAccessory().getImgurl1());

						piassignexternalaccessoryModel
								.setGarmentstyleaccessorydetailcode(garmentstyleaccessorydetail
										.getGarmentstyleaccessorydetailcode());

						lstResult.add(piassignexternalaccessoryModel);
					}
				}
			}

			// Collections.sort(lstPiassignexternalaccessoryModel,
			// PiassignexternalaccessoryModel.accessoryNameComparator);

			return lstResult;
		} catch (Exception e) {
			log.error(String.format(
					"getAllGarmentstyle in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * H??m l???y ra c??c pi assign external ???? save r
	 */
	public List<PiassignexternalaccessoryModel> getListPiassignexternalaccessorySaved(
			String lotNumber) {
		log.debug(String.format(
				"getListPiassignexternalaccessoryByLotNumber in class: %s",
				getClass()));
		try {
			// list k???t qu??? tr??? v???
			List<PiassignexternalaccessoryModel> lstResult = new ArrayList<PiassignexternalaccessoryModel>();
			PiassignexternalaccessoryModel piassignexternalaccessoryModel;

			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumber(lotNumber);
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
				// if estimate m?? >0 th?? m???i add v?? list
				// if(piassignexternalaccessory.getEstimateqty()>0){
				piassignexternalaccessoryModel = new PiassignexternalaccessoryModel();
				// set id c???a t???ng PiassignexternalaccessoryModel t???i ????y
				piassignexternalaccessoryModel
						.setPiassignexternalaccessorycode(piassignexternalaccessory
								.getPiassignexternalaccessorycode());
				piassignexternalaccessoryModel
						.setAccessoryCode(piassignexternalaccessory
								.getAccessory().getAccessorycode());
				piassignexternalaccessoryModel
						.setAccessoryName(piassignexternalaccessory
								.getAccessory().getName());
				piassignexternalaccessoryModel
						.setColorcode(piassignexternalaccessory.getColor()
								.getColorcode());
				piassignexternalaccessoryModel
						.setColorName(piassignexternalaccessory.getColor()
								.getDescription());
				piassignexternalaccessoryModel
						.setSizecode(piassignexternalaccessory
								.getPigriddetail().getSize().getSizecode());
				piassignexternalaccessoryModel
						.setSizename(piassignexternalaccessory
								.getPigriddetail().getSize().getSizename());
				piassignexternalaccessoryModel
						.setGarmentstylecode(piassignexternalaccessory
								.getGarmentstyle().getGarmentstylecode());
				piassignexternalaccessoryModel
						.setGarmentstyleName(piassignexternalaccessory
								.getGarmentstyle().getDescription());
				piassignexternalaccessoryModel.setLotnumber(lotNumber);
				piassignexternalaccessoryModel
						.setPigriddetail(piassignexternalaccessory
								.getPigriddetail().getPigriddetail());
				piassignexternalaccessoryModel
						.setEstimateqty(piassignexternalaccessory
								.getEstimateqty());
				piassignexternalaccessoryModel
						.setMode(piassignexternalaccessory.getAccessory()
								.getMode());
				piassignexternalaccessoryModel
						.setImgurl(piassignexternalaccessory.getAccessory()
								.getImgurl1());

				piassignexternalaccessoryModel
						.setGarmentstyleaccessorydetailcode(piassignexternalaccessory
								.getGarmentstyleaccessorydetail()
								.getGarmentstyleaccessorydetailcode());

				lstResult.add(piassignexternalaccessoryModel);
				// }
			}

			// sort theo accessory name
			Collections.sort(lstResult,
					PiassignexternalaccessoryModel.accessoryNameComparator);
			return lstResult;
		} catch (RuntimeException re) {
			log.error(String
					.format("getListPiassignexternalaccessoryByLotNumber in class %s has error: %s",
							getClass(), re.getMessage()));
			throw re;
		}
	}

	/**
	 * This function is used to get List Piassignexternalaccessory By LotNumber And AccessoryCode |
	 *  H??m ??c d??ng ????? l???y Piassignexternalaccessory hi???n th??? l??n l??c edit l???i wasted percentage
	 * @param lotNumber
	 * @param accessoryCode
	 * @return
	 */
	public List<PiassignexternalaccessoryModel> getListPiassignexternalaccessoryByLotNumberAndAccessoryCode(
			String lotNumber, String accessoryCode) {
		log.debug(String.format(
				"getListPiassignexternalaccessoryByLotNumber in class: %s",
				getClass()));
		try {
			// list k???t qu??? tr??? v???
			List<PiassignexternalaccessoryModel> lstResult = new ArrayList<PiassignexternalaccessoryModel>();
			PiassignexternalaccessoryModel piassignexternalaccessoryModel;

			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumberAndAccessoryCode(
							lotNumber, accessoryCode);
			
			//m??ng x??? l?? display name
			String[] splitGarmentStyleCode;
			
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
				// if estimate m?? >0 th?? m???i add v?? list
				if (piassignexternalaccessory.getEstimateqty() > 0) {
					piassignexternalaccessoryModel = new PiassignexternalaccessoryModel();
					piassignexternalaccessoryModel
							.setPiassignexternalaccessorycode(piassignexternalaccessory
									.getPiassignexternalaccessorycode());
					piassignexternalaccessoryModel
							.setAccessoryCode(piassignexternalaccessory
									.getAccessory().getAccessorycode());
					piassignexternalaccessoryModel
							.setAccessoryName(piassignexternalaccessory
									.getAccessory().getName());
//					piassignexternalaccessoryModel
//							.setImgurl(piassignexternalaccessory.getAccessory()
//									.getImgurl1());
					piassignexternalaccessoryModel
							.setGarmentstylecode(piassignexternalaccessory
									.getGarmentstyle().getGarmentstylecode());
					piassignexternalaccessoryModel
							.setGarmentstyleName(piassignexternalaccessory
									.getGarmentstyle().getDescription());
					piassignexternalaccessoryModel
							.setColorcode(piassignexternalaccessory.getColor()
									.getColorcode());
					piassignexternalaccessoryModel
							.setColorName(piassignexternalaccessory.getColor()
									.getDescription());
					piassignexternalaccessoryModel
							.setSizecode(piassignexternalaccessory
									.getPigriddetail().getSize().getSizecode());
					piassignexternalaccessoryModel
							.setSizename(piassignexternalaccessory
									.getPigriddetail().getSize().getSizename());
					piassignexternalaccessoryModel
							.setEstimateqty(piassignexternalaccessory
									.getEstimateqty());
					piassignexternalaccessoryModel
							.setMode(piassignexternalaccessory.getAccessory()
									.getMode());
					piassignexternalaccessoryModel
							.setTypecode(piassignexternalaccessory
									.getPigriddetail().getSize().getType()
									.getTypecode());
					piassignexternalaccessoryModel
							.setAccessorySupplierShortname(piassignexternalaccessory
									.getOrderexternalaccessory() == null ? ""
									: piassignexternalaccessory
											.getOrderexternalaccessory()
											.getAccessorysupplier()
											.getShortname());
					
					splitGarmentStyleCode = piassignexternalaccessory.getGarmentstyle().getGarmentstylecode().split("@@@");
					piassignexternalaccessoryModel.setGarmentStyleDisplayName(splitGarmentStyleCode[0]);

					lstResult.add(piassignexternalaccessoryModel);
				}
			}

			// sort theo accessory name
			Collections.sort(lstResult,
					PiassignexternalaccessoryModel.accessoryNameComparator);
			return lstResult;
		} catch (RuntimeException re) {
			log.error(String
					.format("getListPiassignexternalaccessoryByLotNumber in class %s has error: %s",
							getClass(), re.getMessage()));
			throw re;
		}
	}
	
	/**
	 * This function is used to check If PiAssignedExternalAccessory Already Have Specific Consumption
	 * @param lotNumber
	 * @param accessoryCode
	 * @return true l?? c?? r, false th?? v???n c??n null
	 */
	public boolean checkIfPiAssignedExternalAccessoryAlreadyHaveSpecificConsumption(String lotNumber, String accessoryCode) {
		log.debug(String.format("checkIfPiAssignedExternalAccessoryAlreadyHaveSpecificConsumption in class: %s",
				getClass()));
		try {
			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumberAndAccessoryCode(
							lotNumber, accessoryCode);
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
				if(piassignexternalaccessory.getSpecificconsumption()!=null)
					return true;
			}
			return false;
		} catch (RuntimeException re) {
			log.error(String.format(
					"checkIfPiAssignedExternalAccessoryAlreadyHaveSpecificConsumption in class %s has error: %s",
					getClass(), re.getMessage()));
			throw re;
		}
	}
	
	/**
	 * This function is used to get Specificconsumption from table PiAssignedExternalAccessory
	 * @param lotNumber
	 * @param accessoryCode
	 * @return
	 */
	public Float getPiAssignedExternalAccessorySpecificConsumption(String lotNumber, String accessoryCode) {
		log.debug(String.format("getPiAssignedExternalAccessorySpecificConsumption in class: %s",
				getClass()));
		try {
			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumberAndAccessoryCode(
							lotNumber, accessoryCode);
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
					return piassignexternalaccessory.getSpecificconsumption();
			}
			return (float) 0;
		} catch (RuntimeException re) {
			log.error(String.format(
					"getPiAssignedExternalAccessorySpecificConsumption in class %s has error: %s",
					getClass(), re.getMessage()));
			throw re;
		}
	}
	
	/**
	 * This function is used to edit Wasted Percentage 
	 * @param lotNumber
	 * @param accessoryCode
	 * @param wastedPercentage
	 * @return
	 */
	public boolean editWastedPercentage(String lotNumber, String accessoryCode, Float wastedPercentage) {
		log.debug(String.format("editWastedPercentage in class: %s",
				getClass()));
		try {
			List<Piassignexternalaccessory> lstByLotNo = dao
					.getListPiassignexternalaccessoryByLotNumberAndAccessoryCode(
							lotNumber, accessoryCode);
			for (Piassignexternalaccessory piassignexternalaccessory : lstByLotNo) {
				piassignexternalaccessory.setSpecificconsumption(wastedPercentage);
				dao.save(piassignexternalaccessory);
			}
			return true;
		} catch (RuntimeException re) {
			log.error(String.format(
					"editWastedPercentage in class %s has error: %s",
					getClass(), re.getMessage()));
			throw re;
		}
	}
}
