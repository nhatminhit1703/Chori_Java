package com.chori.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chori.AbstractDao;
import com.chori.AbstractServiceImpl;
import com.chori.dao.FpiDao;
import com.chori.dao.FpidetailDao;
import com.chori.dao.PIDao;
import com.chori.dao.PigriddetailDao;
import com.chori.entity.Fpi;
import com.chori.entity.Fpidetail;
import com.chori.entity.Pigriddetail;
import com.chori.model.ColorForPiModel;
import com.chori.model.FpidetailModel;
import com.chori.model.GarmentForPiModel;
import com.chori.model.TypeForPiModel;

@Service("fpidetailService")
public class FpidetailServiceImpl extends AbstractServiceImpl<Fpidetail, Integer>
implements FpidetailService{
	private FpidetailDao dao;
	
	@Autowired
	private PigriddetailDao pigriddetailDao;
	
	@Autowired
	private PIDao piDao;
	
	@Autowired
	private FpiDao fpiDao;
	
	@Autowired
	public FpidetailServiceImpl(
			@Qualifier("fpidetailDao") AbstractDao<Fpidetail, Integer> abstractDao) {
		super(abstractDao);
		this.dao = (FpidetailDao) abstractDao;
	}
	
	/**
	 * This function is used to calculate total fpi
	 * @param lotnumber
	 * @param version
	 * @return
	 */
	public int calculateTotalFpi(String lotnumber, Integer version) {
		log.info(String.format("calculateTotalFpi in class: %s", getClass()));
		try {
			log.debug("calculateTotalFpi and return result");
			int totalFpi =0;
			List<Fpidetail> lst = dao.getListFpidetailByLotNumberAndVersion(lotnumber, version);
			for (Fpidetail fpidetail : lst) {
				totalFpi+= fpidetail.getFpivalue();
			}
			log.debug("calculateTotalFpi successfully");
			return totalFpi;
		} catch (Exception e) {
			log.error(String.format(
					"calculateTotalFpi in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	
	public List<FpidetailModel> getListFpiDetailByLotNumberAndVersion(String lotnumber, Integer version){
		//l???y list pi grid detail qua lot number
		List<Pigriddetail> lstPigriddetail = pigriddetailDao.getListPigriddetailByPigridcode(piDao.findById(lotnumber).getPigrid().getPigridcode());
		
		//l???y list grid fpi qua lot no v?? version
		List<Fpidetail> lstFpidetail = dao.getListFpidetailByLotNumberAndVersion(lotnumber, version);
		
		Integer totalFpiPcs = calculateTotalFpi(lotnumber, version);
		Integer totalFaPcs = 0;
		//t??nh totalFa: l???p qua list grid pi detail r t??nh
		for (Pigriddetail pigriddetail : lstPigriddetail) {
			//b??? c??c gi?? tr??? null, kh??c null m???i +
			if(pigriddetail.getFavalue()!=null)
				totalFaPcs += pigriddetail.getFavalue();
		}
		
		//T??nh total cho color --------------------------
		//khai b??o set ????? l??u list ma??
		Set<String> setColor= new HashSet<String>();
		//l???p qua list grid fpi tr??n
		for (Fpidetail fpidetail : lstFpidetail) {
			//add m??u v?? set
			if(!setColor.contains(fpidetail.getColor().getColorcode())){
				setColor.add(fpidetail.getColor().getColorcode());
			}
		}
		
		//l???p qua set color, g??n code v?? gi?? tr??? cho t???ng m??u
		List<ColorForPiModel> lstColorForPiModel = new ArrayList<ColorForPiModel>();
		ColorForPiModel colorForPiModelTmp;
		for (String string : setColor) {
			colorForPiModelTmp= new ColorForPiModel();
			colorForPiModelTmp.setColorName(string);
			colorForPiModelTmp.setColorFaPcs(0);
			colorForPiModelTmp.setColorFpiPcs(0);
			lstColorForPiModel.add(colorForPiModelTmp);
		}
		
		//l???p qua list fpi grid detail, vs m???i m??u t??nh t???ng s??? l?????ng of ch??ng
		//t???o bi???n l??u ????? c???ng
		Integer sumTmp=0;
		//l???p qua list fpi grid detail
		for (Fpidetail fpidetail : lstFpidetail) {
			for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
				//n???u m??u tr??ng th?? +
				if(colorForPiModel.getColorName().equals(fpidetail.getColor().getColorcode())){
					sumTmp = colorForPiModel.getColorFpiPcs() + fpidetail.getFpivalue();
					colorForPiModel.setColorFpiPcs(sumTmp);
				}
			}
		}
		
		//test luu fa************
		for (Pigriddetail pigriddetail : lstPigriddetail) {
			for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
				//n???u m??u tr??ng th?? +
				if(colorForPiModel.getColorName().equals(pigriddetail.getColor().getColorcode())){
					sumTmp = colorForPiModel.getColorFaPcs() + pigriddetail.getFavalue();
					colorForPiModel.setColorFaPcs(sumTmp);
				}
			}
		}
		
		//T??nh total cho garment name ----------------------
		Set<String> setGarment= new HashSet<String>();
		//l???p qua list grid fpi tr??n, 
		for (Fpidetail fpidetail : lstFpidetail) {
			if(!setGarment.contains(fpidetail.getGarmentstyle().getGarmentstylecode())){
				setGarment.add(fpidetail.getGarmentstyle().getGarmentstylecode());
			}
		}
		
		//l???p qua set garment, g??n code v?? gi?? tr??? cho t???ng garment
		List<GarmentForPiModel> lstGarmentForPiModel= new ArrayList<GarmentForPiModel>();
		GarmentForPiModel garmentForPiModelTmp;
		for (String string : setGarment) {
			garmentForPiModelTmp = new GarmentForPiModel();
			garmentForPiModelTmp.setGarmentstyle(string);
			garmentForPiModelTmp.setGarmentstyleFaPcs(0);
			garmentForPiModelTmp.setGarmentstyleFpiPcs(0);
			lstGarmentForPiModel.add(garmentForPiModelTmp);
		}
		
		//l???p qua list fpi grid detail, vs m???i garment t??nh t???ng s??? l?????ng of ch??ng
		//t???o bi???n l??u ????? c???ng
//		Integer sumTmp=0;
		//l???p qua list fpi grid detail
		for (Fpidetail fpidetail : lstFpidetail) {
			for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
				//n???u garment style tr??ng th?? +
				if(garmentForPiModel.getGarmentstyle().equals(fpidetail.getGarmentstyle().getGarmentstylecode())){
					sumTmp = garmentForPiModel.getGarmentstyleFpiPcs()+ fpidetail.getFpivalue();
					garmentForPiModel.setGarmentstyleFpiPcs(sumTmp);
				}
			}
		}
		
		//test luu fa************
				for (Pigriddetail pigriddetail : lstPigriddetail) {
					for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
						//n???u garment style tr??ng th?? +
						if(garmentForPiModel.getGarmentstyle().equals(pigriddetail.getGarmentstyle().getGarmentstylecode())){
							sumTmp = garmentForPiModel.getGarmentstyleFaPcs()+ pigriddetail.getFavalue();
							garmentForPiModel.setGarmentstyleFaPcs(sumTmp);
						}
					}
				}
		
		//T??nh total cho type ----------------------
		Set<String> setType = new HashSet<String>();
		//l???p qua list grid fpi tr??n, 
		for (Fpidetail fpidetail : lstFpidetail) {
			if(!setType.contains(fpidetail.getSize().getType().getTypecode())){
				setType.add(fpidetail.getSize().getType().getTypecode());
			}
		}
		
		List<TypeForPiModel> lstTypeForPiModel= new ArrayList<TypeForPiModel>();
		//l???p qua set type, g??n code v?? gi?? tr??? cho t???ng type
		TypeForPiModel typeForPiModelTmp;
		for (String string : setType) {
			typeForPiModelTmp = new TypeForPiModel();
			typeForPiModelTmp.setTypecode(string);
			typeForPiModelTmp.setTypeFaPcs(0);
			typeForPiModelTmp.setTypeFpiPcs(0);
			lstTypeForPiModel.add(typeForPiModelTmp);
		}
		
		//l???p qua list fpi grid detail, vs m???i type t??nh t???ng s??? l?????ng of ch??ng
		//t???o bi???n l??u ????? c???ng
//		Integer sumTmp=0;
		//l???p qua list fpi grid detail
		for (Fpidetail fpidetail : lstFpidetail) {
			for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
				//n???u garment type tr??ng th?? +
				if(typeForPiModel.getTypecode().equals(fpidetail.getSize().getType().getTypecode())){
					sumTmp = typeForPiModel.getTypeFpiPcs()+ fpidetail.getFpivalue();
					typeForPiModel.setTypeFpiPcs(sumTmp);
				}
			}
		}
		
		//test luu fa************
		for (Pigriddetail pigriddetail : lstPigriddetail) {
			for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
				//n???u garment type tr??ng th?? +
				if(typeForPiModel.getTypecode().equals(pigriddetail.getSize().getType().getTypecode())){
					sumTmp = typeForPiModel.getTypeFaPcs()+ pigriddetail.getFavalue();
					typeForPiModel.setTypeFaPcs(sumTmp);
				}
			}
		}
		
//		for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
//			System.err.println(typeForPiModel);
//		}
		
		List<FpidetailModel> lstResult = new ArrayList<FpidetailModel>();
		FpidetailModel fpidetailModel;
		//g??n model
		for (Fpidetail fpidetail : lstFpidetail) {
			fpidetailModel = new FpidetailModel();
			fpidetailModel.setFpidetailcode(fpidetail.getFpidetailcode());
			fpidetailModel.setTotalFpiPcs(totalFpiPcs);
			fpidetailModel.setTotalFaPcs(totalFaPcs);
			fpidetailModel.setColorName(fpidetail.getColor().getDescription());
			//l???p qua list color, n???u tr??ng th?? g??n gi?? tr???
			for (ColorForPiModel colorForPiModel : lstColorForPiModel) {
				if(fpidetail.getColor().getColorcode().equals(colorForPiModel.getColorName())){
					fpidetailModel.setColorFpiPcs(colorForPiModel.getColorFpiPcs());
					//test g??n fa
					fpidetailModel.setColorFaPcs(colorForPiModel.getColorFaPcs());
					//end test g??n fa
				}
			}
			
			fpidetailModel.setGarmentstyle(fpidetail.getGarmentstyle().getGarmentstylecode());
			//l???p qua list garment, n???u tr??ng th?? g??n gi?? tr???
			for (GarmentForPiModel garmentForPiModel : lstGarmentForPiModel) {
				if(fpidetail.getGarmentstyle().getGarmentstylecode().equals(garmentForPiModel.getGarmentstyle())){
					fpidetailModel.setGarmentstyleFpiPcs(garmentForPiModel.getGarmentstyleFpiPcs());
					//test g??n fa
					fpidetailModel.setGarmentstyleFaPcs(garmentForPiModel.getGarmentstyleFaPcs());
					//end test g??n fa
				}
			}
			
			fpidetailModel.setImgUrl(fpidetail.getGarmentstyle().getImgurl1());
			fpidetailModel.setTypecode(fpidetail.getSize().getType().getTypecode());
			//l???p qua list type, n???u tr??ng th?? g??n gi?? tr???
			for (TypeForPiModel typeForPiModel : lstTypeForPiModel) {
				if(fpidetail.getSize().getType().getTypecode().equals(typeForPiModel.getTypecode())){
					fpidetailModel.setTypeFpiPcs(typeForPiModel.getTypeFpiPcs());
					//test g??n fa
					fpidetailModel.setTypeFaPcs(typeForPiModel.getTypeFaPcs());
					//end test g??n fa
				}
			}
			
			fpidetailModel.setSizecode(fpidetail.getSize().getSizecode());
			fpidetailModel.setSizename(fpidetail.getSize().getSizename());
			fpidetailModel.setFpiPcs(fpidetail.getFpivalue());
			
			fpidetailModel
					.setFaPcs(pigriddetailDao
							.getFaOFPigriddetailByPigridcodeColorCodeGarmentStyleCodeSizeCode(
									piDao.findById(lotnumber).getPigrid()
											.getPigridcode(), fpidetail
											.getColor().getColorcode(),
									fpidetail.getGarmentstyle()
											.getGarmentstylecode(), fpidetail
											.getSize().getSizecode()));
			
			lstResult.add(fpidetailModel);
		}
		
		for (FpidetailModel fpidetailModelTmp : lstResult) {
			System.err.println(fpidetailModelTmp);
		}
		
		return lstResult;
	}
	
	/**
	 * This function is used to get List Fpi Version By LotNumber
	 * @param lotnumber
	 * @return
	 */
	public List<Integer> getListFpiVersionByLotNumber(String lotnumber) {
		log.info(String.format("getListFpiVersionByLotNumber in class: %s", getClass()));
		try {
			log.debug("getListFpiVersionByLotNumber and return result");
			List<Integer> lstResult= new ArrayList<Integer>();
			List<Fpi> lst = fpiDao.getListFpiByLotNumber(lotnumber);
			for (Fpi fpi : lst) {
				lstResult.add(fpi.getVersion());
			}
			log.debug("getListFpiVersionByLotNumber successfully");
			return lstResult;
		} catch (Exception e) {
			log.error(String.format(
					"getListFpiVersionByLotNumber in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
}
