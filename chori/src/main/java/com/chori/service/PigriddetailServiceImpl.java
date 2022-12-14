package com.chori.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chori.AbstractDao;
import com.chori.AbstractServiceImpl;
import com.chori.dao.ColorDao;
import com.chori.dao.FabricinformationDao;
import com.chori.dao.GarmentConsumptionDao;
import com.chori.dao.GarmentConsumptionDetailDao;
import com.chori.dao.GarmentstyleDao;
import com.chori.dao.PIAssignFabricDetailDao;
import com.chori.dao.PIDao;
import com.chori.dao.PigridDao;
import com.chori.dao.PigriddetailDao;
import com.chori.dao.SizeDao;
import com.chori.dao.UserDao;
import com.chori.entity.Garmentconsumption;
import com.chori.entity.Garmentconsumptiondetail;
import com.chori.entity.Pi;
import com.chori.entity.Piassignfabricdetail;
import com.chori.entity.Pigriddetail;
import com.chori.entity.Unit;
import com.chori.entity.Width;
import com.chori.model.CalculateFAValueModel;
import com.chori.model.ColorAndQty;
import com.chori.model.ColorForPiModel;
import com.chori.model.GarmentForPiModel;
import com.chori.model.GarmentStyleAndQty;
import com.chori.model.PiAndQty;
import com.chori.model.PiGridDetailModel;
import com.chori.model.TypeAndQty;
import com.chori.model.TypeForPiModel;
import com.chori.model.UnitModel;
import com.chori.model.WidthModel;


@Repository("pigriddetailService")
public class PigriddetailServiceImpl extends
		AbstractServiceImpl<Pigriddetail, Integer> implements
		PigriddetailService {
	
//	@Autowired
	private PigriddetailDao dao;

	@Autowired
	ColorDao colorDao;

	@Autowired
	PigridDao pigridDao;

	@Autowired
	GarmentstyleDao garmentstyleDao;

	@Autowired
	UserDao userDao;

	@Autowired
	private PIDao piDao;
	
	@Autowired 
	private SizeDao sizeDao;
	 
	@Autowired
	private GarmentConsumptionDao garmentconsumptionDao;
	
	@Autowired
	private GarmentConsumptionDetailDao garmentconsumptiondetailDao;
	
	@Autowired
	private PigriddetailDao pigriddetailDao;
	
	@Autowired
	private PIAssignFabricDetailDao piassignfabricdetailDao;
	
	@Autowired
	private FabricinformationDao fabricinformationDao;

	// public PigriddetailServiceImpl(){
	// }
	@Autowired
	public PigriddetailServiceImpl(
			@Qualifier("pigriddetailDao") AbstractDao<Pigriddetail, Integer> abstractDao) {
		super(abstractDao);
		this.dao = (PigriddetailDao) abstractDao;
	}

	public List<PiGridDetailModel> getAllPiGridDetailModel() {
		log.info(String.format("getAllPiGridDetailModel in class: %s",
				getClass()));
		try {
			log.debug("get all Pi Grid detail in DB after that return a list piGridDetail");
			List<Pigriddetail> lstPiGridDetail = dao.getAll();

			PiGridDetailModel pigriddetailmodel;
			List<PiGridDetailModel> lst = new ArrayList<PiGridDetailModel>();

			for (Pigriddetail pigriddetail : lstPiGridDetail) {

				pigriddetailmodel = new PiGridDetailModel();
				pigriddetailmodel.setPigriddetail(pigriddetail
						.getPigriddetail());
				pigriddetailmodel.setColor((pigriddetail.getColor()
						.getColorcode()));
				pigriddetailmodel.setPigrid(pigriddetail.getPigrid()
						.getPigridcode());
				pigriddetailmodel.setGarmentstyle(pigriddetail
						.getGarmentstyle().getGarmentstylecode());
				pigriddetailmodel.setSizecode(pigriddetail.getSize().getSizecode());
				pigriddetailmodel.setPcs(pigriddetail.getPcs());
				pigriddetailmodel.setBarCode(pigriddetail.getBarcode());
				lst.add(pigriddetailmodel);
			}
			log.debug("getAllPiGridDetailModel successfully");
			return lst;
		} catch (Exception e) {
			log.error(String.format(
					"getAllFabricAssignment in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	@Override
	public PiGridDetailModel findPiGridDetailModelById(int pigriddetailCode) {
		log.info(String.format(
				"findPiGridDetailModelById with param 'pigriddetailCode' in class: %s", getClass()));
		try {
			PiGridDetailModel pgdm = new PiGridDetailModel();
			Pigriddetail pgd = dao.findById(pigriddetailCode);
			
			pgdm.setPigriddetail(pgd.getPigriddetail());
			pgdm.setPigrid(pgd.getPigrid().getPigridcode());
			pgdm.setSizecode(pgd.getSize().getSizecode());
			pgdm.setGarmentstyle(pgd.getGarmentstyle().getGarmentstylecode());
			pgdm.setColor(pgd.getColor().getColorcode());
			pgdm.setBarCode(pgd.getBarcode());
			pgdm.setPcs(pgd.getPcs());

		
			log.debug("findPiGridDetailModelById successfully");
			return pgdm;
		} catch (Exception e) {
			log.error(String
					.format("findPiGridDetailModelById with param 'pigriddetailCode' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	
	
	@Override
	public boolean addPigriddetail(PiGridDetailModel pgdm) {
		try {
			Pigriddetail pgd = new Pigriddetail();
			
			pgd.setPigriddetail(pgdm.getPigriddetail());
			pgd.setPigrid(pigridDao.findById(pgdm.getPigrid()));
			pgd.setColor(colorDao.findById(pgdm.getColor()));
			pgd.setGarmentstyle(garmentstyleDao.findById(pgdm.getGarmentstyle()));
			pgd.setSize(sizeDao.findById(pgdm.getSizecode()));
			pgd.setPcs(pgdm.getPcs()==null?0:pgdm.getPcs());
			pgd.setBarcode(pgdm.getBarCode());
			
			
			dao.save(pgd);
			log.debug("addPigriddetail successfullly");
			return true;
		} catch (Exception e) {
			log.error(String.format(
					"addPigriddetail with param 'PiGridDetailModel' in class: %s has error: %s",
					getClass(), e.getMessage()));
			System.err.println(String.format(
					"addPigriddetail with param 'PiGridDetailModel' in class: %s has error: %s",
					getClass(), e.getMessage()));
			return false;
		}

	}
	
	
	@Override
	public boolean editPigriddetail(PiGridDetailModel pgdm) {
		log.info(String.format(
				"editPigriddetail with param 'PiGridDetailModel' in class: %s",
				getClass()));
		try {
			Pigriddetail pgd=dao.findById(pgdm.getPigriddetail());
			
			pgd.setPigrid(pigridDao.findById(pgdm.getPigrid()));
			pgd.setColor(colorDao.findById(pgdm.getColor()));
			pgd.setGarmentstyle(garmentstyleDao.findById(pgdm.getGarmentstyle()));
			pgd.setSize(sizeDao.findById(pgdm.getSizecode()));
			pgd.setPcs(pgdm.getPcs()==null?0:pgdm.getPcs());
			dao.update(pgd);
			log.debug("editPigriddetail successfully");
			return true;
		} catch (Exception e) {
			log.error(String
					.format("editPigriddetail with param 'PiGridDetailModel' in class: %s has error: %s",
							getClass(), e.getMessage()));
			System.err
					.println(String
							.format("editPigriddetail with param 'PiGridDetailModel' in class: %s has error: %s",
									getClass(), e.getMessage()));
			return false;
		}
	}

	
	

	/**
	 * This function is used to get PI grid detail by Lot number H??m l???y grid
	 * detail ????? show l??n view trc khi assign v???i
	 * 
	 * @param lotNumber
	 * @return
	 */
	// public List<PiGridDetailModel> getListPiGridDetailModelByLotNo(String
	// lotNumber){
	public List<PiGridDetailModel> getListPiGridDetailModelByLotNo(
			String lotNumber) {
		log.info(String.format("getListPiGridDetailModelByLotNo in class: %s",
				getClass()));
		try {
			log.debug("getListPiGridDetailModelByLotNo in DB after that return a list piGridDetail");

			// T???o ra 1 ??.t?????ng PiAndQty
			PiAndQty piAndQty = new PiAndQty();
			ColorAndQty colorAndQty1 = new ColorAndQty();
			// hard code m??u tr???ng
			colorAndQty1.setColorcode("CWHT");

			// ko ph???i m??u tr???ng
			ColorAndQty colorAndQty2 = new ColorAndQty();
			colorAndQty2.setColorcode("NonCWHT");

			piAndQty.getSetColorAndQty().add(colorAndQty1);
			piAndQty.getSetColorAndQty().add(colorAndQty2);

			// L???y pi grid t??? lot No
			Pi pi = piDao.findById(lotNumber);
			Integer pigridCode = pi.getPigrid().getPigridcode();
			// g??n lotNo, pigrid code cho piAndQty
			piAndQty.setLotnumber(lotNumber);
			piAndQty.setPigridcode(pigridCode);
			// l???y PI grid detail qua lot No
			List<Pigriddetail> lstPigriddetail = dao
					.getListPigriddetailByPigridcode(pigridCode);

			Integer totalPcs = 0;
			Integer totalPcsByColorWhite = 0;
			Integer totalPcsByColorNotWhite = 0;

			// set garmentStyleCode theo white v?? theo color, ????? l??u c??c
			// garmentStyleCode
			Set<String> garmentStyleCodeByWhite = new HashSet<String>();
			Set<String> garmentStyleCodeByColor = new HashSet<String>();

			// l???p qua lstPigriddetail, t??nh t???ng pcs, t???ng pcs theo m??u tr???ng,
			// theo m??u # tr???ng
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				totalPcs += pigriddetail.getPcs();
				if (pigriddetail.getColor().getColorcode().equals("CWHT")) {
					// n???u l?? m??u tr???ng th?? +
					totalPcsByColorWhite += pigriddetail.getPcs();
					// add garment style v?? set
					if (!garmentStyleCodeByWhite.contains(pigriddetail
							.getGarmentstyle().getGarmentstylecode()))
						garmentStyleCodeByWhite.add(pigriddetail
								.getGarmentstyle().getGarmentstylecode());
				} else if (!pigriddetail.getColor().getColorcode()
						.equals("CWHT")) {
					// n???u kh??c tr???ng m???i +
					totalPcsByColorNotWhite += pigriddetail.getPcs();
					// add garment style v?? set
					if (!garmentStyleCodeByColor.contains(pigriddetail
							.getGarmentstyle().getGarmentstylecode()))
						garmentStyleCodeByColor.add(pigriddetail
								.getGarmentstyle().getGarmentstylecode());
				}
			}
			// g??n total pcs
			piAndQty.setPcs(totalPcs);
			// g??n pcs cho m??u tr???ng v?? color
			piAndQty.getSetColorAndQty().get(0).setPcs(totalPcsByColorWhite);
			piAndQty.getSetColorAndQty().get(1).setPcs(totalPcsByColorNotWhite);

			// t???o tmpList Garment Style v?? Qty ????? l??u
			// List<GarmentStyleAndQty> lstTmpGarmentStyleAndQty = new
			// ArrayList<GarmentStyleAndQty>();

			// kh???i t???o 1 bi???n GarmentStyleAndQty m???i l???n l???p
			GarmentStyleAndQty tmpGarmentStyleAndQty;

			// l???p qua list c??c garment style theo white, th??m v?? list c???a
			// piAndQty
			for (String string : garmentStyleCodeByWhite) {
				tmpGarmentStyleAndQty = new GarmentStyleAndQty();
				tmpGarmentStyleAndQty.setGarmentstylecode(string);
				piAndQty.getSetColorAndQty().get(0).getSetGarmentStyleAndQty()
						.add(tmpGarmentStyleAndQty);// n??y m???i add t??n, ch??a add
													// s??? l?????ng
			}

			for (String string : garmentStyleCodeByColor) {
				tmpGarmentStyleAndQty = new GarmentStyleAndQty();
				tmpGarmentStyleAndQty.setGarmentstylecode(string);
				piAndQty.getSetColorAndQty().get(1).getSetGarmentStyleAndQty()
						.add(tmpGarmentStyleAndQty);
			}

			/**
			 * l???p qua t???ng list garmentStyleCode by white v?? color ????? t??nh t???ng
			 * cho t???ng garment style
			 */

			// t???o 1 list ????? l??u list pi grid detail b???i Pigridcode, ColorCode,
			// GarmentStyleCode
			List<Pigriddetail> lstTmpPigriddetail;

			// l???p qua t???ng list garmentStyleCode by white v?? color ????? t??nh t???ng
			// cho t???ng garment style
			Integer totalTmpForGarmentStyle = 0;
			List<GarmentStyleAndQty> lstTmpGarmentStyleAndQty = piAndQty
					.getSetColorAndQty().get(0).getSetGarmentStyleAndQty();// by
																			// white
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty) {
				lstTmpPigriddetail = dao
						.getListPigriddetailByPigridcodeColorCodeGarmentStyleCode(
								pigridCode, "CWHT",
								garmentStyleAndQty.getGarmentstylecode());
				for (Pigriddetail pigriddetail : lstTmpPigriddetail) {
					totalTmpForGarmentStyle += pigriddetail.getPcs();
				}
				garmentStyleAndQty.setPcs(totalTmpForGarmentStyle);

				System.err.println(totalTmpForGarmentStyle);

				totalTmpForGarmentStyle = 0;
				lstTmpPigriddetail.clear();
			}

			// for color
			List<GarmentStyleAndQty> lstTmpGarmentStyleAndQty1 = piAndQty
					.getSetColorAndQty().get(1).getSetGarmentStyleAndQty();// by
																			// color
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty1) {
				lstTmpPigriddetail = dao
						.getListPigriddetailByPigridcodeColorCodeGarmentStyleCodeNotWhite(
								pigridCode, "CWHT",
								garmentStyleAndQty.getGarmentstylecode());
				for (Pigriddetail pigriddetail : lstTmpPigriddetail) {
					totalTmpForGarmentStyle += pigriddetail.getPcs();
				}
				garmentStyleAndQty.setPcs(totalTmpForGarmentStyle);

				System.err.println(totalTmpForGarmentStyle);

				totalTmpForGarmentStyle = 0;
				lstTmpPigriddetail.clear();
			}

			/**
			 * t??nh type v?? pcs
			 */
			// t??nh type
			Set<String> setTmpType = new HashSet<String>();
			// t???o m???i 1 TypeAndQty ????? l???p v?? l??u
			TypeAndQty typeAndQty;

			// l???p qua list garment name theo white, ????? l???y ra t???t c??? c??c type
			// theo garment name ????
			lstTmpGarmentStyleAndQty = piAndQty.getSetColorAndQty().get(0)
					.getSetGarmentStyleAndQty();// by white
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty) {
				// l???y list pigrid detail theo color, garment name
				lstTmpPigriddetail = dao
						.getListPigriddetailByPigridcodeColorCodeGarmentStyleCode(
								pigridCode, "CWHT",
								garmentStyleAndQty.getGarmentstylecode());
				for (Pigriddetail pigriddetail : lstTmpPigriddetail) {
					// n???u setTmpType n??y m?? ch??a t???n t???i type code th??
					if (!setTmpType.contains(pigriddetail.getSize().getType()
							.getTypecode())) {
						setTmpType.add(pigriddetail.getSize().getType()
								.getTypecode());
					}

					// l???p qua setTmpType
					for (String string : setTmpType) {
						typeAndQty = new TypeAndQty();
						typeAndQty.setTypecode(string);
						garmentStyleAndQty.getSetSizeAndQty().add(typeAndQty);
					}

					setTmpType.clear();
				}
				lstTmpPigriddetail.clear();
			}

			// lstTmpGarmentStyleAndQty.clear();
			// l???p qua list garment name theo color, ????? l???y ra t???t c??? c??c type
			// theo garment name ????
			lstTmpGarmentStyleAndQty1 = piAndQty.getSetColorAndQty().get(1)
					.getSetGarmentStyleAndQty();// by color
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty1) {
				// l???y list pigrid detail theo color, garment name
				lstTmpPigriddetail = dao
						.getListPigriddetailByPigridcodeColorCodeGarmentStyleCodeNotWhite(
								pigridCode, "CWHT",
								garmentStyleAndQty.getGarmentstylecode());
				for (Pigriddetail pigriddetail : lstTmpPigriddetail) {
					// n???u setTmpType n??y m?? ch??a t???n t???i type code th??
					if (!setTmpType.contains(pigriddetail.getSize().getType()
							.getTypecode())) {
						setTmpType.add(pigriddetail.getSize().getType()
								.getTypecode());
					}

					// l???p qua setTmpType
					for (String string : setTmpType) {
						typeAndQty = new TypeAndQty();
						typeAndQty.setTypecode(string);
						garmentStyleAndQty.getSetSizeAndQty().add(typeAndQty);
					}

					setTmpType.clear();
				}
				lstTmpPigriddetail.clear();
			}

			// T??nh pcs
			lstTmpGarmentStyleAndQty = piAndQty.getSetColorAndQty().get(0)
					.getSetGarmentStyleAndQty();// by white
			List<TypeAndQty> lstTmpTypeAndQty;
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty) {
				// lstTmpPigriddetail =
				// dao.getListPigriddetailByPigridcodeColorCodeGarmentStyleCodeTypeCodeForWhite(pigridCode,
				// "CWHT", garmentStyleAndQty.getGarmentstylecode(), );
				lstTmpTypeAndQty = garmentStyleAndQty.getSetSizeAndQty();
				for (TypeAndQty typeAndQty2 : lstTmpTypeAndQty) {
					// t??m list pi grid detail qua Pigridcode ColorCode
					// GarmentStyleCode TypeCode
					lstTmpPigriddetail = dao
							.getListPigriddetailByPigridcodeColorCodeGarmentStyleCodeTypeCodeForWhite(
									pigridCode, "CWHT",
									garmentStyleAndQty.getGarmentstylecode(),
									typeAndQty2.getTypecode());
					for (Pigriddetail pigriddetail : lstTmpPigriddetail) {
						totalTmpForGarmentStyle += pigriddetail.getPcs();
					}
					typeAndQty2.setPcs(totalTmpForGarmentStyle);
					totalTmpForGarmentStyle = 0;
				}
			}

			lstTmpGarmentStyleAndQty = piAndQty.getSetColorAndQty().get(1)
					.getSetGarmentStyleAndQty();// by color
			// List<TypeAndQty> lstTmpTypeAndQty;
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty) {
				// lstTmpPigriddetail =
				// dao.getListPigriddetailByPigridcodeColorCodeGarmentStyleCodeTypeCodeForWhite(pigridCode,
				// "CWHT", garmentStyleAndQty.getGarmentstylecode(), );
				lstTmpTypeAndQty = garmentStyleAndQty.getSetSizeAndQty();
				for (TypeAndQty typeAndQty2 : lstTmpTypeAndQty) {
					// t??m list pi grid detail qua Pigridcode ColorCode
					// GarmentStyleCode TypeCode
					lstTmpPigriddetail = dao
							.getListPigriddetailByPigridcodeColorCodeGarmentStyleCodeTypeCodeNotForWhite(
									pigridCode, "CWHT",
									garmentStyleAndQty.getGarmentstylecode(),
									typeAndQty2.getTypecode());
					for (Pigriddetail pigriddetail : lstTmpPigriddetail) {
						totalTmpForGarmentStyle += pigriddetail.getPcs();
					}
					typeAndQty2.setPcs(totalTmpForGarmentStyle);
					totalTmpForGarmentStyle = 0;
				}
			}

			/**
			 * l???p qua, l???y list pi grid detail theo color white
			 */
			// t???o m???i 1 pi grid detail model
			List<PiGridDetailModel> lst1 = new ArrayList<PiGridDetailModel>();
			PiGridDetailModel piGridDetailModel;

			lstTmpGarmentStyleAndQty = piAndQty.getSetColorAndQty().get(0)
					.getSetGarmentStyleAndQty();// by white
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty) {
				lstTmpTypeAndQty = garmentStyleAndQty.getSetSizeAndQty();
				for (TypeAndQty typeAndQty2 : lstTmpTypeAndQty) {
					piGridDetailModel = new PiGridDetailModel();
					piGridDetailModel.setColor("CWHT");
					piGridDetailModel.setGarmentstyle(garmentStyleAndQty
							.getGarmentstylecode());
					piGridDetailModel.setGarmentstylePcs(garmentStyleAndQty
							.getPcs());
					piGridDetailModel.setTotalPcs(piAndQty.getPcs());
					piGridDetailModel.setColorPcs(piAndQty.getSetColorAndQty()
							.get(0).getPcs());
					piGridDetailModel.setTypecode(typeAndQty2.getTypecode());
					piGridDetailModel.setTypePcs(typeAndQty2.getPcs());

					lst1.add(piGridDetailModel);
				}
			}

			lstTmpGarmentStyleAndQty = piAndQty.getSetColorAndQty().get(1)
					.getSetGarmentStyleAndQty();// by white
			for (GarmentStyleAndQty garmentStyleAndQty : lstTmpGarmentStyleAndQty) {
				lstTmpTypeAndQty = garmentStyleAndQty.getSetSizeAndQty();
				for (TypeAndQty typeAndQty2 : lstTmpTypeAndQty) {
					piGridDetailModel = new PiGridDetailModel();
					piGridDetailModel.setColor("NonCWHT");
					piGridDetailModel.setGarmentstyle(garmentStyleAndQty
							.getGarmentstylecode());
					piGridDetailModel.setGarmentstylePcs(garmentStyleAndQty
							.getPcs());
					piGridDetailModel.setTotalPcs(piAndQty.getPcs());
					piGridDetailModel.setColorPcs(piAndQty.getSetColorAndQty()
							.get(1).getPcs());
					piGridDetailModel.setTypecode(typeAndQty2.getTypecode());
					piGridDetailModel.setTypePcs(typeAndQty2.getPcs());

					lst1.add(piGridDetailModel);
				}
			}

			List<Pigriddetail> lstPiGridDetail = dao
					.getListPigriddetailByPigridcode(pigridCode);

			List<PiGridDetailModel> lst2 = new ArrayList<PiGridDetailModel>();

			for (Pigriddetail pigriddetail : lstPiGridDetail) {

				piGridDetailModel = new PiGridDetailModel();
				piGridDetailModel.setPigriddetail(pigriddetail
						.getPigriddetail());
				piGridDetailModel.setColor(pigriddetail.getColor()
						.getColorcode().equals("CWHT") ? "CWHT" : "NonCWHT");
				piGridDetailModel.setColorName(pigriddetail.getColor()
						.getColorcode().equals("CWHT") ? "White" : "Color");
				piGridDetailModel.setPigrid(pigriddetail.getPigrid()
						.getPigridcode());
				piGridDetailModel.setGarmentstyle(pigriddetail
						.getGarmentstyle().getGarmentstylecode());
				piGridDetailModel.setImgUrl1(pigriddetail.getGarmentstyle()
						.getImgurl1());
				piGridDetailModel.setImgUrl2(pigriddetail.getGarmentstyle()
						.getImgurl2());
				piGridDetailModel.setImgUrl3(pigriddetail.getGarmentstyle()
						.getImgurl3());
				piGridDetailModel.setImgUrl4(pigriddetail.getGarmentstyle()
						.getImgurl4());
				piGridDetailModel.setImgUrl5(pigriddetail.getGarmentstyle()
						.getImgurl5());
				piGridDetailModel.setTypecode(pigriddetail.getSize().getType()
						.getTypecode());
				piGridDetailModel.setSizecode(pigriddetail.getSize()
						.getSizecode());
				piGridDetailModel.setSizename(pigriddetail.getSize()
						.getSizename());
				piGridDetailModel.setPcs(pigriddetail.getPcs());
				piGridDetailModel.setTotalPcs(piAndQty.getPcs());

				lst2.add(piGridDetailModel);
			}

			// l???p qua 2 list
			for (PiGridDetailModel piGridDetailModel1 : lst1) {
				for (PiGridDetailModel piGridDetailModel2 : lst2) {
					if (piGridDetailModel1.getColor().equals(
							piGridDetailModel2.getColor())
							&& piGridDetailModel1.getGarmentstyle().equals(
									piGridDetailModel2.getGarmentstyle())
							&& piGridDetailModel1.getTypecode().equals(
									piGridDetailModel2.getTypecode())) {
						piGridDetailModel2.setColorPcs(piGridDetailModel1
								.getColorPcs());
						piGridDetailModel2
								.setGarmentstylePcs(piGridDetailModel1
										.getGarmentstylePcs());
						piGridDetailModel2.setTypePcs(piGridDetailModel1
								.getTypePcs());
					}
				}
			}

			// return piAndQty;
			return lst2;
		} catch (Exception e) {
			log.error(String
					.format("getListPiGridDetailModelByLotNo in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	////FA Value
	// get assign quantity by lotNo,garmentStyleCode, colorCode, fabricNo from Piassignfabricdetail
	@Override
	public Integer getAssignQtyByLotnoGarmentstyleCodeColorCodeFabricNo(String lotNo, String colorCode, String garmentstyleCode , String fabricNo){
		log.info(String.format("getAssignQtyByLotnoGarmentstyleCodeColorCodeFabricNo in class: %s", getClass()));
		try {
			List<Piassignfabricdetail> lstPiassignfabricdetail = piassignfabricdetailDao.getAll();	
			Integer totalAssignQty = 0;
			for(Piassignfabricdetail piassignfabricdetail : lstPiassignfabricdetail){
				if(piassignfabricdetail.getColor().getColorcode().equals(colorCode)
						&& piassignfabricdetail.getGarmentstyle()
							.getGarmentstylecode().equals(garmentstyleCode)
						&& piassignfabricdetail.getPiassignfabric().getPi()
							.getLotnumber().equals(lotNo)
						&& piassignfabricdetail.getPiassignfabric().getId().getFabricno().equals(fabricNo)){
					
					log.debug("getAssignQtyByLotnoGarmentstyleCodeColorCodeFabricNo successfully!");
					totalAssignQty += piassignfabricdetail.getAssignquantity();
					return totalAssignQty;
				}
			}
			return null;
		}catch(Exception e){
			log.error(String.format(
					"getAssignQtyByLotnoGarmentstyleCodeColorCode in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}		
	}
	
	//find pigridCode by lotNumber in Pi
	@Override
	public Integer findPIGridCodeByLotNo (String lotNo){
		log.info(String.format("findPIGridCodeByLotNo in class: %s", getClass()));
		try{
			
			return piDao.findById(lotNo).getPigrid().getPigridcode();
		}catch (Exception e){
			log.error(String.format(
					"findPIGridCodeByLotNo in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	
	//get total Pcs by pigridCode, garmentstyleCode, colorCode
	@Override
	public Double getTotalPcs(String lotNo , String garmentstyleCode, String colorCode){
		log.info(String.format("getTotalPcs in class: %s", getClass()));
		try{
			List<Pigriddetail> lstPigriddetail = pigriddetailDao.getAll();
			Double totalPcs = (double) 0;	
			for(Pigriddetail pigriddetail : lstPigriddetail){
				if(pigriddetail.getPigrid().getPigridcode().equals(findPIGridCodeByLotNo(lotNo))
						&& pigriddetail.getColor().getColorcode().equals(colorCode)
						&& pigriddetail.getGarmentstyle().getGarmentstylecode().equals(garmentstyleCode)){
					totalPcs += pigriddetail.getPcs();									
				}
			}		
			return (double) totalPcs;
		}catch (Exception e){
			log.error(String.format(
					"getTotalPcs in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	//convert pcs to percent 
	@Override
	public Float convertPcsToPercent ( String lotNo , String garmentstyleCode, String colorCode, Integer sizeCode){
		log.info(String.format("convertPcsToPercent in class: %s", getClass()));
		try{
			List<Pigriddetail> lstPigriddetail = pigriddetailDao.getAll();
		//	Float percentPcs = (float) 0 ;
			for(Pigriddetail pigriddetail : lstPigriddetail){
				if(pigriddetail.getPigrid().getPigridcode().equals(findPIGridCodeByLotNo(lotNo))
						&& pigriddetail.getColor().getColorcode().equals(colorCode)
						&& pigriddetail.getGarmentstyle().getGarmentstylecode().equals(garmentstyleCode)
						&& pigriddetail.getSize().getSizecode().equals(sizeCode)){
					
				return (float) (pigriddetail.getPcs() * 100 / getTotalPcs(lotNo, garmentstyleCode, colorCode));
				}
			}
		}catch(Exception e){
			log.error(String.format(
					"convertPcsToPercent in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
		return null;		
	}
	//get garmentconsumptionCode from garmentconsumtion
	@Override
	public Integer findGarmentconsumptionCode(String garmentstyleCode, String customerCode, Integer sizeCode){
		log.info(String.format("findGarmentConsumtionCode in class: %s", getClass()));
		try{
			List<Garmentconsumption> lstGarmentconsumption = garmentconsumptionDao.getAll();
			for(Garmentconsumption garmentconsumption : lstGarmentconsumption){
				if(garmentconsumption.getSize()
							.getSizecode().equals(sizeCode)
						&& garmentconsumption.getCustomer()
							.getCustomercode().equals(customerCode)
						&& garmentconsumption.getGarmentstyle()
							.getGarmentstylecode().equals(garmentstyleCode)){
				return garmentconsumption.getGarmentconsumptioncode();
				}
			}
		}catch(Exception e){
			log.error(String.format(
					"findGarmentconsumptionCode in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
		return null;		
	}
	
	// get Consumption Value by garmentconsumptionCode and widthCode in Garmentconsumptiondetail
	@Override
	public Float getConsumptionValue(String garmentstyleCode, String customerCode, Integer sizeCode , String fabricNo){
		log.info(String.format("getConsumptionValue in class: %s", getClass()));
		try{
			List<Garmentconsumptiondetail> lstGarmentconsumptiondetail = garmentconsumptiondetailDao.getAll();
			for(Garmentconsumptiondetail garmentconsumptiondetail : lstGarmentconsumptiondetail){
				if(garmentconsumptiondetail.getWidth()
						.getWidthcode().equals(fabricinformationDao.findById(fabricNo).getWidth().getWidthcode())
					&& garmentconsumptiondetail.getGarmentconsumption().getGarmentconsumptioncode()
						.equals(findGarmentconsumptionCode(garmentstyleCode, customerCode, sizeCode))){
					return garmentconsumptiondetail.getConvalue();
				}
			}
			return null;
		}catch(Exception e){
			log.error(String.format(
					"getConsumptionValue in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	
	//calculate FA Value by calculatefavalueModel
	@Override
	public Integer calculateFAValue(CalculateFAValueModel calculatefavalueModel){
		log.info(String.format("calculateFAValue in class: %s", getClass()));
		try{
//			CalculateFAValueModel calculatefavaluemodel = new CalculateFAValueModel();
//			List<Pigriddetail> lstPigriddetail = pigriddetailDao.getAll();
//			Integer faValue = 0;
//			for(Pigriddetail pigriddetail : lstPigriddetail){
//				if(pigriddetail.getSize().getSizecode().equals(calculatefavaluemodel.getSizeCode())
//						&& pigriddetail.getPigrid().getPigridcode()
//							.equals(findPIGridCodeByLotNo(calculatefavaluemodel.getLotNo()))
//						&& pigriddetail.getGarmentstyle().getGarmentstylecode()
//							.equals(calculatefavaluemodel.getGarmentstyleCode())
//						&& pigriddetail.getColor().getColorcode()
//							.equals(calculatefavaluemodel.getColorCode())){
			Integer assignQty = getAssignQtyByLotnoGarmentstyleCodeColorCodeFabricNo(calculatefavalueModel.getLotNo()
					, calculatefavalueModel.getColorCode()
					, calculatefavalueModel.getGarmentstyleCode() 
					, calculatefavalueModel.getFabricNo());
			System.err.println("assignQty" + assignQty);
			Float convalue = getConsumptionValue(calculatefavalueModel.getGarmentstyleCode()
					, calculatefavalueModel.getCustomerCode()
					, calculatefavalueModel.getSizeCode()
					, calculatefavalueModel.getFabricNo());
			System.err.println("convalue" + convalue);
			Float percent = convertPcsToPercent(calculatefavalueModel.getLotNo()
					, calculatefavalueModel.getGarmentstyleCode()
					, calculatefavalueModel.getColorCode()
					, calculatefavalueModel.getSizeCode());
			System.err.println("Percent" + percent);

			return (int) ((percent * assignQty /100) / convalue);
//					return (int) ((convertPcsToPercent(calculatefavaluemodel.getLotNo()
//												, calculatefavaluemodel.getGarmentstyleCode()
//												, calculatefavaluemodel.getColorCode()
//												, calculatefavaluemodel.getSizeCode())
//											* getAssignQtyByLotnoGarmentstyleCodeColorCodeFabricNo(calculatefavaluemodel.getLotNo()
//													, calculatefavaluemodel.getColorCode()
//													, calculatefavaluemodel.getGarmentstyleCode() 
//													, calculatefavaluemodel.getFabricNo()) / 100)
//											/ getConsumptionValue(calculatefavaluemodel.getGarmentstyleCode()
//													, calculatefavaluemodel.getCustomerCode()
//													, calculatefavaluemodel.getSizeCode()
//													, calculatefavaluemodel.getFabricNo()));
//				}
//				
//			}
//			return 0;
		}catch(Exception e){
			log.error(String.format(
					"calculateFAValue in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
//		return null;

	}
	
//	public List<PiGridDetailModel> getListPiGridDetail(String lotNumber){
	public List<PiGridDetailModel> getListPiGridDetail(String lotNumber){
		log.info(String.format("getListPiGridDetail in class: %s", getClass()));
		try{
			//l???y pi grid detail qua lot no
			List<Pigriddetail> lstPigriddetail = dao.getListPigriddetailByPigridcode(piDao.findById(lotNumber).getPigrid().getPigridcode());
			
			//t??nh total pcs, fa
			Integer totalPcs = 0;
			Integer totalFa = 0;
			//l???p qua list grid pi ????? t??nh total
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				totalPcs+= pigriddetail.getPcs();
				totalFa+= pigriddetail.getFavalue();
			}
			
			//T??nh total cho color --------------------------
			//khai b??o set ????? l??u list m??u
			Set<String> setColor= new HashSet<String>();
			//l???p qua list grid pi tr??n
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				//add m??u v?? set
				if(!setColor.contains(pigriddetail.getColor().getColorcode())){
					setColor.add(pigriddetail.getColor().getColorcode());
				}
			}
			
			//l???p qua set color, g??n code v?? gi?? tr??? cho t???ng m??u
			List<ColorForPiModel> lstColorForPiModel = new ArrayList<ColorForPiModel>();
			ColorForPiModel colorForPiModelTmp;
			for (String string : setColor) {
				colorForPiModelTmp= new ColorForPiModel();
				colorForPiModelTmp.setColorName(string);
				colorForPiModelTmp.setColorPcs(0);
				colorForPiModelTmp.setColorFaPcs(0);
				lstColorForPiModel.add(colorForPiModelTmp);
			}
			
			//l???p qua list fpi grid detail, vs m???i m??u t??nh t???ng s??? l?????ng of ch??ng
			//t???o bi???n l??u ????? c???ng
			Integer sumPcsTmp=0;
			Integer sumFaTmp=0;
			//l???p qua list fpi grid detail
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
					//n???u m??u tr??ng th?? +
					if(colorForPiModel.getColorName().equals(pigriddetail.getColor().getColorcode())){
						sumPcsTmp = colorForPiModel.getColorPcs() + pigriddetail.getPcs();
						colorForPiModel.setColorPcs(sumPcsTmp);
						sumFaTmp = colorForPiModel.getColorFaPcs() + pigriddetail.getFavalue();
						colorForPiModel.setColorFaPcs(sumFaTmp);
					}
				}
			}
			
			//T??nh total cho garment name ----------------------
			Set<String> setGarment= new HashSet<String>();
			//l???p qua list grid pi tr??n, 
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				if(!setGarment.contains(pigriddetail.getGarmentstyle().getGarmentstylecode())){
					setGarment.add(pigriddetail.getGarmentstyle().getGarmentstylecode());
				}
			}
			
			//l???p qua set garment, g??n code v?? gi?? tr??? cho t???ng garment
			List<GarmentForPiModel> lstGarmentForPiModel= new ArrayList<GarmentForPiModel>();
			GarmentForPiModel garmentForPiModelTmp;
			for (String string : setGarment) {
				garmentForPiModelTmp = new GarmentForPiModel();
				garmentForPiModelTmp.setGarmentstyle(string);
				garmentForPiModelTmp.setGarmentstyleFaPcs(0);
				garmentForPiModelTmp.setGarmentstylePcs(0);
				lstGarmentForPiModel.add(garmentForPiModelTmp);
			}
			
			//l???p qua list pi grid detail, vs m???i garment t??nh t???ng s??? l?????ng of ch??ng
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
					//n???u garment style tr??ng th?? +
					if(garmentForPiModel.getGarmentstyle().equals(pigriddetail.getGarmentstyle().getGarmentstylecode())){
						sumPcsTmp = garmentForPiModel.getGarmentstylePcs()+ pigriddetail.getPcs();
						garmentForPiModel.setGarmentstylePcs(sumPcsTmp);
						
						sumFaTmp = garmentForPiModel.getGarmentstyleFaPcs()+ pigriddetail.getFavalue();
						garmentForPiModel.setGarmentstyleFaPcs(sumFaTmp);
					}
				}
			}
			
			//T??nh total cho type ----------------------
			Set<String> setType = new HashSet<String>();
			//l???p qua list grid pi tr??n, 
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				if(!setType.contains(pigriddetail.getSize().getType().getTypecode())){
					setType.add(pigriddetail.getSize().getType().getTypecode());
				}
			}
			
			List<TypeForPiModel> lstTypeForPiModel= new ArrayList<TypeForPiModel>();
			//l???p qua set type, g??n code v?? gi?? tr??? cho t???ng type
			TypeForPiModel typeForPiModelTmp;
			for (String string : setType) {
				typeForPiModelTmp = new TypeForPiModel();
				typeForPiModelTmp.setTypecode(string);
				typeForPiModelTmp.setTypeFaPcs(0);
				typeForPiModelTmp.setTypePcs(0);
				lstTypeForPiModel.add(typeForPiModelTmp);
			}
			
			//l???p qua list pi grid detail, vs m???i type t??nh t???ng s??? l?????ng of ch??ng
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
					//n???u garment type tr??ng th?? +
					if(typeForPiModel.getTypecode().equals(pigriddetail.getSize().getType().getTypecode())){
						sumPcsTmp = typeForPiModel.getTypePcs()+ pigriddetail.getPcs();
						typeForPiModel.setTypePcs(sumPcsTmp);
						sumFaTmp = typeForPiModel.getTypeFaPcs()+ pigriddetail.getFavalue();
						typeForPiModel.setTypeFaPcs(sumFaTmp);
					}
				}
			}
			
	//		for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
	//			System.err.println(typeForPiModel);
	//		}
			
			List<PiGridDetailModel> lstResult= new ArrayList<PiGridDetailModel>();
			PiGridDetailModel piGridDetailModel;
			
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				piGridDetailModel = new PiGridDetailModel();
				piGridDetailModel.setPigriddetail(pigriddetail.getPigriddetail());
				piGridDetailModel.setPigrid(pigriddetail.getPigrid().getPigridcode());
				piGridDetailModel.setTotalPcs(totalPcs);
				piGridDetailModel.setTotalFaPcs(totalFa);
				piGridDetailModel.setColor(pigriddetail.getColor().getColorcode());
				piGridDetailModel.setColorName(pigriddetail.getColor().getDescription());
				//l???p qua list color, n???u tr??ng th?? g??n gi?? tr???
				for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
					if(pigriddetail.getColor().getColorcode().equals(colorForPiModel.getColorName())){
						piGridDetailModel.setColorPcs(colorForPiModel.getColorPcs());
						//test g??n fa
						piGridDetailModel.setColorFaPcs(colorForPiModel.getColorFaPcs());
						//end test g??n fa
					}
				}
				
				piGridDetailModel.setGarmentstyle(pigriddetail.getGarmentstyle().getGarmentstylecode());
				//l???p qua list garment, n???u tr??ng th?? g??n gi?? tr???
				for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
					if(pigriddetail.getGarmentstyle().getGarmentstylecode().equals(garmentForPiModel.getGarmentstyle())){
						piGridDetailModel.setGarmentstylePcs(garmentForPiModel.getGarmentstylePcs());
						//test g??n fa
						piGridDetailModel.setGarmentstyleFaPcs(garmentForPiModel.getGarmentstyleFaPcs());
						//end test g??n fa
					}
				}
				
				piGridDetailModel.setImgUrl1(pigriddetail.getGarmentstyle().getImgurl1());
				piGridDetailModel.setTypecode(pigriddetail.getSize().getType().getTypecode());
				//l???p qua list type, n???u tr??ng th?? g??n gi?? tr???
				for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
					if(pigriddetail.getSize().getType().getTypecode().equals(typeForPiModel.getTypecode())){
						piGridDetailModel.setTypePcs(typeForPiModel.getTypePcs());
						//test g??n fa
						piGridDetailModel.setTypeFaPcs(typeForPiModel.getTypeFaPcs());
						//end test g??n fa
					}
				}
				
				piGridDetailModel.setSizecode(pigriddetail.getSize().getSizecode());
				piGridDetailModel.setSizename(pigriddetail.getSize().getSizename());
				piGridDetailModel.setPcs(pigriddetail.getPcs());
				//n???u fa m?? ch??a c?? th?? set = 0
				piGridDetailModel.setFaValue(pigriddetail.getFavalue()==null?0:pigriddetail.getFavalue());
				
				lstResult.add(piGridDetailModel);
			}
			
			for (PiGridDetailModel piGridDetailModelTmp : lstResult) {
				System.err.println(piGridDetailModelTmp);
			}
			
			return lstResult;
		}catch(Exception e){
			log.error(String.format(
					"getListPiGridDetail in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * This function is used to check if lotnumber is assign fabric already
	 * @param lotnumber
	 * @return true if already assign fabric, false if not assign fabric
	 */
	public boolean isAssignFabricYet(String lotnumber){
		log.info(String.format("isAssignFabricYet in class: %s", getClass()));
		try{
			List<Pigriddetail> lstPigriddetail = dao.getListPigriddetailByPigridcode(piDao.findById(lotnumber).getPigrid().getPigridcode());
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				if(pigriddetail.getFavalue()==null)
					return false;
			}
			return true;
		}catch(Exception e){
			log.error(String.format(
					"isAssignFabricYet in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	
	public List<PiGridDetailModel> getListPiGridDetailNotAssignFabric(String lotNumber){
		log.info(String.format("getListPiGridDetail in class: %s", getClass()));
		try{
			//l???y pi grid detail qua lot no
			List<Pigriddetail> lstPigriddetail = dao.getListPigriddetailByPigridcode(piDao.findById(lotNumber).getPigrid().getPigridcode());
			
			//t??nh total pcs, fa
			Integer totalPcs = 0;
//			Integer totalFa = 0;
			//l???p qua list grid pi ????? t??nh total
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				totalPcs+= pigriddetail.getPcs();
//				totalFa+= pigriddetail.getFavalue();
			}
			
			//T??nh total cho color --------------------------
			//khai b??o set ????? l??u list m??u
			Set<String> setColor= new HashSet<String>();
			//l???p qua list grid pi tr??n
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				//add m??u v?? set
				if(!setColor.contains(pigriddetail.getColor().getColorcode())){
					setColor.add(pigriddetail.getColor().getColorcode());
				}
			}
			
			//l???p qua set color, g??n code v?? gi?? tr??? cho t???ng m??u
			List<ColorForPiModel> lstColorForPiModel = new ArrayList<ColorForPiModel>();
			ColorForPiModel colorForPiModelTmp;
			for (String string : setColor) {
				colorForPiModelTmp= new ColorForPiModel();
				colorForPiModelTmp.setColorName(string);
				colorForPiModelTmp.setColorPcs(0);
//				colorForPiModelTmp.setColorFaPcs(0);
				lstColorForPiModel.add(colorForPiModelTmp);
			}
			
			//l???p qua list fpi grid detail, vs m???i m??u t??nh t???ng s??? l?????ng of ch??ng
			//t???o bi???n l??u ????? c???ng
			Integer sumPcsTmp=0;
//			Integer sumFaTmp=0;
			//l???p qua list fpi grid detail
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
					//n???u m??u tr??ng th?? +
					if(colorForPiModel.getColorName().equals(pigriddetail.getColor().getColorcode())){
						sumPcsTmp = colorForPiModel.getColorPcs() + pigriddetail.getPcs();
						colorForPiModel.setColorPcs(sumPcsTmp);
//						sumFaTmp = colorForPiModel.getColorFaPcs() + pigriddetail.getFavalue();
//						colorForPiModel.setColorFaPcs(sumFaTmp);
					}
				}
			}
			
			//T??nh total cho garment name ----------------------
			Set<String> setGarment= new HashSet<String>();
			//l???p qua list grid pi tr??n, 
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				if(!setGarment.contains(pigriddetail.getGarmentstyle().getGarmentstylecode())){
					setGarment.add(pigriddetail.getGarmentstyle().getGarmentstylecode());
				}
			}
			
			//l???p qua set garment, g??n code v?? gi?? tr??? cho t???ng garment
			List<GarmentForPiModel> lstGarmentForPiModel= new ArrayList<GarmentForPiModel>();
			GarmentForPiModel garmentForPiModelTmp;
			for (String string : setGarment) {
				garmentForPiModelTmp = new GarmentForPiModel();
				garmentForPiModelTmp.setGarmentstyle(string);
//				garmentForPiModelTmp.setGarmentstyleFaPcs(0);
				garmentForPiModelTmp.setGarmentstylePcs(0);
				lstGarmentForPiModel.add(garmentForPiModelTmp);
			}
			
			//l???p qua list pi grid detail, vs m???i garment t??nh t???ng s??? l?????ng of ch??ng
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
					//n???u garment style tr??ng th?? +
					if(garmentForPiModel.getGarmentstyle().equals(pigriddetail.getGarmentstyle().getGarmentstylecode())){
						sumPcsTmp = garmentForPiModel.getGarmentstylePcs()+ pigriddetail.getPcs();
						garmentForPiModel.setGarmentstylePcs(sumPcsTmp);
						
//						sumFaTmp = garmentForPiModel.getGarmentstyleFaPcs()+ pigriddetail.getFavalue();
//						garmentForPiModel.setGarmentstyleFaPcs(sumFaTmp);
					}
				}
			}
			
			//T??nh total cho type ----------------------
			Set<String> setType = new HashSet<String>();
			//l???p qua list grid pi tr??n, 
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				if(!setType.contains(pigriddetail.getSize().getType().getTypecode())){
					setType.add(pigriddetail.getSize().getType().getTypecode());
				}
			}
			
			List<TypeForPiModel> lstTypeForPiModel= new ArrayList<TypeForPiModel>();
			//l???p qua set type, g??n code v?? gi?? tr??? cho t???ng type
			TypeForPiModel typeForPiModelTmp;
			for (String string : setType) {
				typeForPiModelTmp = new TypeForPiModel();
				typeForPiModelTmp.setTypecode(string);
//				typeForPiModelTmp.setTypeFaPcs(0);
				typeForPiModelTmp.setTypePcs(0);
				lstTypeForPiModel.add(typeForPiModelTmp);
			}
			
			//l???p qua list pi grid detail, vs m???i type t??nh t???ng s??? l?????ng of ch??ng
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
					//n???u garment type tr??ng th?? +
					if(typeForPiModel.getTypecode().equals(pigriddetail.getSize().getType().getTypecode())){
						sumPcsTmp = typeForPiModel.getTypePcs()+ pigriddetail.getPcs();
						typeForPiModel.setTypePcs(sumPcsTmp);
//						sumFaTmp = typeForPiModel.getTypeFaPcs()+ pigriddetail.getFavalue();
//						typeForPiModel.setTypeFaPcs(sumFaTmp);
					}
				}
			}
			
	//		for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
	//			System.err.println(typeForPiModel);
	//		}
			
			List<PiGridDetailModel> lstResult= new ArrayList<PiGridDetailModel>();
			PiGridDetailModel piGridDetailModel;
			
			for (Pigriddetail pigriddetail : lstPigriddetail) {
				piGridDetailModel = new PiGridDetailModel();
				piGridDetailModel.setPigriddetail(pigriddetail.getPigriddetail());
				piGridDetailModel.setPigrid(pigriddetail.getPigrid().getPigridcode());
				piGridDetailModel.setTotalPcs(totalPcs);
//				piGridDetailModel.setTotalFaPcs(totalFa);
				piGridDetailModel.setColor(pigriddetail.getColor().getColorcode());
				piGridDetailModel.setColorName(pigriddetail.getColor().getDescription());
				//l???p qua list color, n???u tr??ng th?? g??n gi?? tr???
				for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
					if(pigriddetail.getColor().getColorcode().equals(colorForPiModel.getColorName())){
						piGridDetailModel.setColorPcs(colorForPiModel.getColorPcs());
						//test g??n fa
//						piGridDetailModel.setColorFaPcs(colorForPiModel.getColorFaPcs());
						//end test g??n fa
					}
				}
				
				piGridDetailModel.setGarmentstyle(pigriddetail.getGarmentstyle().getGarmentstylecode());
				//l???p qua list garment, n???u tr??ng th?? g??n gi?? tr???
				for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
					if(pigriddetail.getGarmentstyle().getGarmentstylecode().equals(garmentForPiModel.getGarmentstyle())){
						piGridDetailModel.setGarmentstylePcs(garmentForPiModel.getGarmentstylePcs());
						//test g??n fa
//						piGridDetailModel.setGarmentstyleFaPcs(garmentForPiModel.getGarmentstyleFaPcs());
						//end test g??n fa
					}
				}
				
				piGridDetailModel.setImgUrl1(pigriddetail.getGarmentstyle().getImgurl1());
				piGridDetailModel.setTypecode(pigriddetail.getSize().getType().getTypecode());
				//l???p qua list type, n???u tr??ng th?? g??n gi?? tr???
				for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
					if(pigriddetail.getSize().getType().getTypecode().equals(typeForPiModel.getTypecode())){
						piGridDetailModel.setTypePcs(typeForPiModel.getTypePcs());
						//test g??n fa
//						piGridDetailModel.setTypeFaPcs(typeForPiModel.getTypeFaPcs());
						//end test g??n fa
					}
				}
				
				piGridDetailModel.setSizecode(pigriddetail.getSize().getSizecode());
				piGridDetailModel.setSizename(pigriddetail.getSize().getSizename());
				piGridDetailModel.setPcs(pigriddetail.getPcs());
				//n???u fa m?? ch??a c?? th?? set = 0
//				piGridDetailModel.setFaValue(pigriddetail.getFavalue()==null?0:pigriddetail.getFavalue());
				
				lstResult.add(piGridDetailModel);
			}
			
			for (PiGridDetailModel piGridDetailModelTmp : lstResult) {
				System.err.println(piGridDetailModelTmp);
			}
			
			return lstResult;
		}catch(Exception e){
			log.error(String.format(
					"getListPiGridDetail in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	public boolean deleteAllbyPigridCode(int pigridCode){
		if(dao.deleteAllbyPigridCode(pigridCode)){
		return true;}
	return false;	
	}
	public boolean deletePigriddetail(int pigriddetailCode) {
		log.info(String.format("deletePigriddetail with param 'pigriddetailCode' in class: %s",
				getClass()));
		try {
			Pigriddetail or = dao.findById(pigriddetailCode);
			dao.delete(or);
			return true;
		} catch (Exception e) {
			return false;
		}
}
	
	public List<PiGridDetailModel> getAllPiGridDetailModelbyPigridCode(int pigridcode) {
		log.debug("in unit service list");
		try {
			List<Pigriddetail> lstPigriddetailModel = dao.getListPigriddetailByPigridcode(pigridcode);
			PiGridDetailModel pgdm;
			List<PiGridDetailModel> lst = new ArrayList<PiGridDetailModel>();
			for (Pigriddetail pgd : lstPigriddetailModel) {
				pgdm = new PiGridDetailModel();
				pgdm.setBarCode(pgd.getBarcode());
				pgdm.setColor(pgd.getColor().getColorcode());
				pgdm.setSizecode(pgd.getSize().getSizecode());
				pgdm.setGarmentstyle(pgd.getGarmentstyle().getGarmentstylecode());
				pgdm.setPigriddetail(pgd.getPigriddetail());
				pgdm.setPigrid(pgd.getPigrid().getPigridcode());
				pgdm.setSizename(pgd.getSize().getSizename());
				pgdm.setPcs(pgd.getPcs());
				pgdm.setColorName(pgd.getColor().getDescription());
				

				lst.add(pgdm);
			}
			return lst;
		} catch (NullPointerException ne) {
			log.error("list service estimate err: " + ne.getMessage());
		}
		return null;
	}
}
