package com.chori.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.chori.model.FabricinformationModel;
import com.chori.model.FabricinformationdetailModel;
import com.chori.service.ColorService;
import com.chori.service.FabricinformationService;

@Controller
@RequestMapping(value = "/")
public class FabricinformationController {

	private static final Log log = LogFactory
			.getLog(FabricinformationController.class);

	@Autowired
	FabricinformationService ser;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ColorService colorService;

	private FabricinformationModel fabricInfoAddModel = null;
	private String fabricinformationImg = null;
	private List<FabricinformationdetailModel> lstFabricinformationdetailModel = new ArrayList<FabricinformationdetailModel>();

	/**
	 * This function is used to return view page for list Fabric information
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/listFabricinformation", method = RequestMethod.GET)
	public String listFabricinformation(ModelMap model) {
		log.info(String.format(
				"listFabricinformation with param 'model' in class: %s",
				getClass()));
		try {
			log.debug("listFabricinformation successful");
			FabricinformationModel fabricinformationModel1 = new FabricinformationModel();
			FabricinformationModel fabricinformationModel2 = new FabricinformationModel();

			model.addAttribute("fabricinformationModel1",
					fabricinformationModel1);
			model.addAttribute("fabricinformationModel2",
					fabricinformationModel2);
			model.addAttribute("fabricinformationdetailModel1",
					new FabricinformationdetailModel());

			return "operation/fabricInfomation/listFabricInfomation";
		} catch (Exception e) {
			log.error(String
					.format("listFabricinformation with param 'model' in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to get all fabric information
	 * 
	 * @return list FabricinformationModel as json
	 */
	@RequestMapping(value = "/fabricinformation/list", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllFabricinformation() {
		log.info(String.format("getAllFabricinformation in class %s",
				getClass()));
		try {
			log.debug("getting list of All Fabric information and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			List<FabricinformationModel> ls = ser
					.getAllFabricinformationModel();
			result.put("status", "ok");
			result.put("list", ls);
			log.debug("getAllFabricinformation successful");
			return result;
		} catch (Exception e) {
			log.error(String.format(
					"getAllFabricinformation in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to add new fabric information
	 * 
	 * @param fim
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fabricinformation/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> addNewFabricinformation(
			@Valid @RequestBody FabricinformationModel fim,
			BindingResult bindingResult) {
		log.info(String.format(
				"addNewFabricinformation with params 'fm' in class: %s",
				getClass()));

		System.err.println(fim);
		
		// fabricInfoAddModel =null;
		if (fabricInfoAddModel == null)
			fabricInfoAddModel = new FabricinformationModel();
		// g??n gi?? tr??? c???a fim qua fabricInfoAddModel
		fabricInfoAddModel.setCustomer(fim.getCustomer());
		fabricInfoAddModel.setFabricsupplier(fim.getFabricsupplier());
		fabricInfoAddModel.setIschori(fim.getIschori());
		fabricInfoAddModel.setFabricitem(fim.getFabricitem());
		fabricInfoAddModel.setFabricno(fim.getFabricno());
		fabricInfoAddModel.setComponent(fim.getComponent());
		fabricInfoAddModel.setFactory(fim.getFactory());
		fabricInfoAddModel.setWidthcode(fim.getWidthcode());
		fabricInfoAddModel.setFabricinvoiceno(fim.getFabricinvoiceno());
		fabricInfoAddModel.setCurrencycode(fim.getCurrencycode());
		fabricInfoAddModel.setRemark(fim.getRemark());
		fabricInfoAddModel.setEstimatedelvdate(fim.getEstimatedelvdate());
		fabricInfoAddModel.setActualdelvdate(fim.getActualdelvdate());
		fabricInfoAddModel.setVoucherreceiveddate(fim.getVoucherreceiveddate());
		fabricInfoAddModel.setVouchersentdate(fim.getVouchersentdate());

		fabricInfoAddModel.setFabricimgurl(fabricinformationImg);

		System.err.println("Last: " + fabricInfoAddModel);

		// fabricinformationImg = null;

		try {
			log.debug("addNewFabricinformation and return status as json format");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");

			// n???u ???? t???n t???i ho???c ko nh???p g?? ho???c c?? space ?????u, cu???i; nh???p to??n
			// space
			if (ser.isFabricInformationExistedByFabricNo(fabricInfoAddModel
					.getFabricno())
					|| fabricInfoAddModel.getFabricno().length() == 0
					|| fabricInfoAddModel.getFabricno().length() != fabricInfoAddModel
							.getFabricno().trim().length()
					|| fabricInfoAddModel.getFabricno().trim().length() == 0) {
				result.put("fabricNoStatus", false);
			} else {
				// d??ng ok
				fabricInfoAddModel.setFabricimgurl(fabricinformationImg);

				result.put("addStatus", true);
				result.put("fabricNoStatus", true);
				// c?? v??i tr?????ng h???p add b??n h??m add ???nh lu??n n??n qua ????y add l??
				// v??ng l???i, ki???m tra ???? add ch??a, ch??a th?? m???i add
				if (!ser.isFabricInformationExistedByFabricNo(fabricInfoAddModel
						.getFabricno()))
					result.put("addStatus", ser.addNewFabricInformation(
							fabricInfoAddModel,
							lstFabricinformationdetailModel, "admin"));

				lstFabricinformationdetailModel.clear();
				log.debug("addNewFabricinformation successfully");
			}

			return result;
		} catch (Exception e) {
			log.error(String
					.format("addNewFabricinformation with params 'fm' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to add image part for fabric information
	 * 
	 * @param request
	 * @param fim
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/fabricinformation/addNew", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveApplicationForm(
			HttpServletRequest request, FabricinformationModel fim) {
		Map<String, String> resultMap = new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fabricImage");

		// resultMap.put("path", "choriGarmentStyleImage/" +
		// multipartFile.getOriginalFilename());
		String name = multipartFile.getOriginalFilename();
		String handleFileName = "";

		// Th?? m???c s??? l??u
//		File dir = new File(request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage("fabricInformationLocation",
//								null, null)));
		
		File dir = new File(messageSource.getMessage("UPLOAD_FILE",
								null, null));
		
		if (!dir.exists()) {
			dir.mkdirs();
		}

		handleFileName = name.substring(0, name.lastIndexOf("."))
				+ fim.getFabricno() + new Date().getTime()
				+ name.substring(name.lastIndexOf("."));

//		String path = request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage("fabricInformationLocation",
//								null, null))
//				+ handleFileName;
		
		//fix l???i ???????ng d???n
		String path = 
				messageSource.getMessage("UPLOAD_FILE",
						null, null) + File.separator + handleFileName;
		
		System.out.println(path);
		File file = new File(path);
		try {
			FileCopyUtils.copy(multipartFile.getBytes(), file);

			// /
			if (fabricInfoAddModel == null) {
				// l?? save h??nh trc, n??n l??u bi???n h??nh
				fabricinformationImg = handleFileName;
				// fabricInfoAddModel = null;
			} else if (fabricInfoAddModel != null) {
				// coi nh?? t???o r, ko t???o m???i n???a
				// g??n gi?? tr??? c???a fim qua fabricInfoAddModel
				// n???u ch??a insert db th?? insert trc r m???i update ???nh
				if (!ser.isFabricInformationExistedByFabricNo(fabricInfoAddModel
						.getFabricno())
						&& fabricInfoAddModel.getFabricno().length() > 0
						&& fabricInfoAddModel.getFabricno().length() == fabricInfoAddModel
								.getFabricno().trim().length()
						&& fabricInfoAddModel.getFabricno().trim().length() > 0)
					ser.addNewFabricInformation(fabricInfoAddModel,
							lstFabricinformationdetailModel, "admin");
				System.err.println("Update image after add:" + handleFileName);
				ser.updateImageAfterAddNewFabricInformation(
						fabricInfoAddModel.getFabricno(), handleFileName);
				// m???i add
				fabricinformationImg = handleFileName;
				// fabricInfoAddModel = null;
				// fabricinformationImg =null;
			}
			// /
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	/**
	 * This function is used to add new fabric information detail
	 * 
	 * @param fidm
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fabricinformationDetail/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> addNewFabricinformationDetail(
			@Valid @RequestBody FabricinformationdetailModel fidm,
			BindingResult bindingResult) {
		log.info(String.format(
				"addNewFabricinformationDetail with params 'fm' in class: %s",
				getClass()));

		try {
			log.debug("addNewFabricinformationDetail and return status as json format");

			// ki???m tra xem c?? ch??a? ch??a c?? th?? add, c?? th?? update
			boolean checkContain = false;

			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModel) {
				// tr?????ng h???p c?? r???i
				if (fidm.getColorcode().equals(
						fabricinformationdetailModel.getColorcode())) {
					checkContain = true;
					// //n???u c?? r???i th?? update lu??n
					// fabricinformationdetailModel.setColorcode(fidm.getColorcode());
					// fabricinformationdetailModel.setYardinbl(fidm.getYardinbl());
					// fabricinformationdetailModel.setUnitprice(fidm.getUnitprice());
					// break;
				}
				if (checkContain == true) {
					// n???u c?? r???i th?? update lu??n
					fabricinformationdetailModel.setColorcode(fidm
							.getColorcode());
					fabricinformationdetailModel
							.setYardinbl(fidm.getYardinbl());
					fabricinformationdetailModel.setUnitprice(fidm
							.getUnitprice());
					break;
				}
			}

			// n???u ch??a c?? th?? add
			if (checkContain == false) {
				fidm.setColorName(colorService.findById(fidm.getColorcode())
						.getDescription());
				lstFabricinformationdetailModel.add(fidm);
			}
			//

			System.err.println("Json: " + fidm);

			System.err.println("Json lstFabricinformationdetailModel : "
					+ lstFabricinformationdetailModel);

			// Test
			// int lstLength = lstFabricinformationdetailModel.size();
			// boolean breakFlag= false;
			// for(int i =0;i<lstLength;i++){
			// for(int j =1;j<lstLength-1;j++){
			// if(lstFabricinformationdetailModel.get(i).getColorcode().equals(lstFabricinformationdetailModel.get(j).getColorcode())){
			// lstFabricinformationdetailModel.get(i).setYardinbl(fidm.getYardinbl());
			// lstFabricinformationdetailModel.get(i).setUnitprice(fidm.getUnitprice());
			// breakFlag= true;
			// lstFabricinformationdetailModel.remove(j);
			// break;
			// }
			// }
			// if(breakFlag)
			// break;
			// }
			// End Test

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			log.debug("addNewFabricinformation successfully");
			return result;
		} catch (Exception e) {
			log.error(String
					.format("addNewFabricinformationDetail with params 'fm' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to save image for each fabric information detail
	 * 
	 * @param request
	 * @param fidm
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "fabricinformationDetail/addNew", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveImageFabricinformationDetail(
			HttpServletRequest request, FabricinformationdetailModel fidm) {
		Map<String, String> resultMap = new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest
				.getFile("fabricImageDetail");

		String name = multipartFile.getOriginalFilename();
		String handleFileName = "";

		// N???u th?? m???c ch??a t???o th?? t???o
//		File dir = new File(request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage(
//								"fabricInformationDetailLocation", null, null)));
		
		File dir = new File(messageSource.getMessage(
								"UPLOAD_FILE", null, null));
		
		if (!dir.exists()) {
			dir.mkdirs();
		}

		handleFileName = name.substring(0, name.lastIndexOf("."))
				+ fidm.getColorcode() + new Date().getTime()
				+ name.substring(name.lastIndexOf("."));

//		String path = request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage(
//								"fabricInformationDetailLocation", null, null))
//				+ handleFileName;
		
		String path = messageSource.getMessage(
								"UPLOAD_FILE", null, null)+File.separator
				+ handleFileName;
		
		System.out.println(path);
		File file = new File(path);
		try {
			// ki???m tra xem c?? ch??a? ch??a c?? th?? add, c?? th?? update
			boolean checkContain = false;

			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModel) {
				// tr?????ng h???p c?? r???i
				if (fidm.getColorcode().equals(
						fabricinformationdetailModel.getColorcode())) {
					checkContain = true;
					// //n???u c?? r???i th?? update lu??n
					// fabricinformationdetailModel.setColorcode(fidm.getColorcode());
					// fabricinformationdetailModel.setImgurl(handleFileName);
					// break;
				}
				if (checkContain == true) {
					// n???u c?? r???i th?? update lu??n
					fabricinformationdetailModel.setColorcode(fidm
							.getColorcode());
					fabricinformationdetailModel.setImgurl(handleFileName);
					break;
				}
			}

			// n???u ch??a c?? th?? add
			if (checkContain == false) {
				fidm.setImgurl(handleFileName);
				fidm.setColorName(colorService.findById(fidm.getColorcode())
						.getDescription());
				lstFabricinformationdetailModel.add(fidm);
			}
			// Cu???i c??ng save file
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			System.err.println("Post: " + fidm);

			System.err.println("Post lstFabricinformationdetailModel : "
					+ lstFabricinformationdetailModel);

			// //Test
			// int lstLength = lstFabricinformationdetailModel.size();
			// boolean breakFlag= false;
			// for(int i =0;i<lstLength;i++){
			// for(int j =1;j<lstLength-1;j++){
			// if(lstFabricinformationdetailModel.get(i).getColorcode().equals(lstFabricinformationdetailModel.get(j).getColorcode())){
			// lstFabricinformationdetailModel.get(i).setImgurl(handleFileName);
			// breakFlag= true;
			// lstFabricinformationdetailModel.remove(j);
			// break;
			// }
			// }
			// if(breakFlag)
			// break;
			// }
			// //End Test

		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	/**
	 * This function is used to get list fabric information Detail when adding
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fabricinformationDetailAdd/list", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllFabricinformationDetailAdd() {
		log.info(String.format("getAllFabricinformationDetailAdd in class %s",
				getClass()));
		try {
			log.debug("getting list of All Fabric information detail and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");

			// Test
			int lstLength = lstFabricinformationdetailModel.size();
			boolean breakFlag = false;

			int index = -1;

			for (int i = 0; i < lstLength - 1; i++) {
				for (int j = i + 1; j < lstLength; j++) {
					if (lstFabricinformationdetailModel
							.get(i)
							.getColorcode()
							.equals(lstFabricinformationdetailModel.get(j)
									.getColorcode())) {
						// if(lstFabricinformationdetailModel.get(j).getImgurl()!=null){
						// lstFabricinformationdetailModel.get(i).setImgurl(lstFabricinformationdetailModel.get(j).getImgurl());
						// }
						//
						// if(lstFabricinformationdetailModel.get(j).getUnitprice()!=null){
						// lstFabricinformationdetailModel.get(i).setUnitprice(lstFabricinformationdetailModel.get(j).getUnitprice());
						// }
						//
						// if(lstFabricinformationdetailModel.get(j).getYardinbl()!=null){
						// lstFabricinformationdetailModel.get(i).setYardinbl(lstFabricinformationdetailModel.get(j).getYardinbl());
						// }
						index = j;
						breakFlag = true;
						// lstFabricinformationdetailModel.remove(j);
						break;
					}
				}
				if (breakFlag)
					break;
			}

			if (index > -1) {
				if (lstFabricinformationdetailModel.get(index).getImgurl() != null) {
					lstFabricinformationdetailModel.get(index - 1).setImgurl(
							lstFabricinformationdetailModel.get(index)
									.getImgurl());
				}

				if (lstFabricinformationdetailModel.get(index).getUnitprice() != null) {
					lstFabricinformationdetailModel.get(index - 1)
							.setUnitprice(
									lstFabricinformationdetailModel.get(index)
											.getUnitprice());
				}

				if (lstFabricinformationdetailModel.get(index).getYardinbl() != null) {
					lstFabricinformationdetailModel.get(index - 1).setYardinbl(
							lstFabricinformationdetailModel.get(index)
									.getYardinbl());
				}

				lstFabricinformationdetailModel.remove(index);
			}
			// End Test

			result.put("listFabricinformationDetailAdd",
					lstFabricinformationdetailModel);
			log.debug("getAllFabricinformationDetailAdd successful");

			System.err.println("LstFabricinformationdetailModel : "
					+ lstFabricinformationdetailModel);

			return result;
		} catch (Exception e) {
			log.error(String
					.format("getAllFabricinformationDetailAdd in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to clear all model when close add fabric
	 * information H??m d??ng ????? reset l???i c??c model sau khi b???m cancel add dialog
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fabricinformation/clearDataAfterCloseAddFabInforDialog", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> clearDataAfterCloseAddFabInforDialog() {
		log.info(String.format(
				"clearDataAfterCloseAddFabInforDialog in class %s", getClass()));
		try {
			log.debug("getting list of All Fabric information and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			fabricInfoAddModel = null;
			fabricinformationImg = null;
			lstFabricinformationdetailModel.clear();
			result.put("status", "ok");
			result.put("deleteStatus", true);
			log.debug("clearDataAfterCloseAddFabInforDialog successful");
			return result;
		} catch (Exception e) {
			log.error(String
					.format("clearDataAfterCloseAddFabInforDialog in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function check if a fabric with fabric no is existed
	 * 
	 * @param fabricNo
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformation/isExist/{fabricNo}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> isFabricInformationExist(
//			@PathVariable String fabricNo) {
//		log.info(String.format("isFabricInformationExist in class: %s",
//				getClass()));
//		try {
//			log.debug("check if a factory with factoryCode is existed in DB and return as json format");
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("status", "ok");
//			result.put("isExisted",
//					ser.isFabricInformationExistedByFabricNo(fabricNo));
//			log.debug("check isFabricInformationExist successful");
//			return result;
//		} catch (Exception e) {
//			log.error(String.format(
//					"isFabricInformationExist in class %s has error: %s",
//					getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformation/isExist", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> isFabricInformationExist(
			@RequestBody FabricinformationModel fabricinformationModel) {
		log.info(String.format("isFabricInformationExist in class: %s",
				getClass()));
		try {
			log.debug("check if a factory with factoryCode is existed in DB and return as json format");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			result.put("isExisted",
					ser.isFabricInformationExistedByFabricNo(fabricinformationModel.getFabricno()));
			log.debug("check isFabricInformationExist successful");
			return result;
		} catch (Exception e) {
			log.error(String.format(
					"isFabricInformationExist in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function is used to delete fabric information detail of list add H??m
	 * d??ng ????? x??a 1 ph???n t??? trong list fabric information detail c???a fabric trc
	 * khi add
	 * 
	 * @param colorcode
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformationDetail/delete/{colorcode}", produces = "application/json", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> deleteFabricInformationDetailAdd(
//			@PathVariable String colorcode) {
//		log.info(String.format("deleteFabricInformationDetailAdd in class %s",
//				getClass()));
//		try {
//			log.debug("deleteFabricInformationDetailAdd and return json");
//			Map<String, Object> result = new HashMap<String, Object>();
//			// List<FactoryModel> ls = ser.getAllFactoryModel();
//			result.put("status", "ok");
//			// l???p qua list Fabric information detail Model, n???u color code =
//			// th?? x??a
//			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModel) {
//				if (fabricinformationdetailModel.getColorcode().equals(
//						colorcode)) {
//					lstFabricinformationdetailModel
//							.remove(fabricinformationdetailModel);
//					break;
//				}
//			}
//			result.put("deleteStatus", true);
//			log.debug("deleteFabricInformationDetailAdd successful");
//			return result;
//		} catch (Exception e) {
//			log.error(String
//					.format("deleteFabricInformationDetailAdd in class %s has error: %s",
//							getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformationDetail/delete", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFabricInformationDetailAdd(
			@RequestBody FabricinformationdetailModel fabricinformationdetailModelParam) {
		log.info(String.format("deleteFabricInformationDetailAdd in class %s",
				getClass()));
		try {
			log.debug("deleteFabricInformationDetailAdd and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			// List<FactoryModel> ls = ser.getAllFactoryModel();
			result.put("status", "ok");
			// l???p qua list Fabric information detail Model, n???u color code =
			// th?? x??a
			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModel) {
				if (fabricinformationdetailModel.getColorcode().equals(
						fabricinformationdetailModelParam.getColorcode())) {
					lstFabricinformationdetailModel
							.remove(fabricinformationdetailModel);
					break;
				}
			}
			result.put("deleteStatus", true);
			log.debug("deleteFabricInformationDetailAdd successful");
			return result;
		} catch (Exception e) {
			log.error(String
					.format("deleteFabricInformationDetailAdd in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * This function return a fabric information detail as json to send into
	 * view (find by id) for editing a fabric information detail before add H??m
	 * tr??? v??? 1 fabric information detail khi nh???n ch???n edit 1 p.t??? ????? edit khi
	 * ch???n drop down list
	 * 
	 * @param colorcode
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformationDetail/detail/{colorcode}", produces = "application/json", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> get1FabricInformationDetailAdd(
//			@PathVariable String colorcode) {
//		log.info(String.format("get1FabricInformationDetailAdd in class %s",
//				getClass()));
//		try {
//			log.debug("get1FabricInformationDetailAdd and return json");
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("status", "ok");
//			
//			//n???u ch??a c?? trong list th?? kh???i t???o m???i unit price, yar in bl = 0
//			FabricinformationdetailModel fabInforDetailModel = new FabricinformationdetailModel();
//			fabInforDetailModel.setColorcode(colorcode);
//			fabInforDetailModel.setUnitprice((float)0);
//			fabInforDetailModel.setYardinbl((double)0);
//			result.put("fabricinformationdetailModel",
//					fabInforDetailModel);
//			
//			// l???p qua list Fabric information detail Model, n???u color code
//			// gi???ng th?? tr??? v??? ????? ????a l??n view 1 Fabric information detail
//			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModel) {
//				if (fabricinformationdetailModel.getColorcode().equals(
//						colorcode)) {
//					result.put("fabricinformationdetailModel",
//							fabricinformationdetailModel);
//					break;
//				}
//			}
//
//			log.debug("get1FabricInformationDetailAdd successful");
//			return result;
//		} catch (Exception e) {
//			log.error(String.format(
//					"get1FabricInformationDetailAdd in class %s has error: %s",
//					getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformationDetail/detail", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get1FabricInformationDetailAdd(
			@RequestBody FabricinformationdetailModel fabricinformationdetailModelParam) {
		log.info(String.format("get1FabricInformationDetailAdd in class %s",
				getClass()));
		try {
			log.debug("get1FabricInformationDetailAdd and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			
			//n???u ch??a c?? trong list th?? kh???i t???o m???i unit price, yar in bl = 0
			FabricinformationdetailModel fabInforDetailModel = new FabricinformationdetailModel();
			fabInforDetailModel.setColorcode(fabricinformationdetailModelParam.getColorcode());
			fabInforDetailModel.setUnitprice((float)0);
			fabInforDetailModel.setYardinbl((double)0);
			result.put("fabricinformationdetailModel",
					fabInforDetailModel);
			
			// l???p qua list Fabric information detail Model, n???u color code
			// gi???ng th?? tr??? v??? ????? ????a l??n view 1 Fabric information detail
			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModel) {
				if (fabricinformationdetailModel.getColorcode().equals(
						fabricinformationdetailModelParam.getColorcode())) {
					result.put("fabricinformationdetailModel",
							fabricinformationdetailModel);
					break;
				}
			}

			log.debug("get1FabricInformationDetailAdd successful");
			return result;
		} catch (Exception e) {
			log.error(String.format(
					"get1FabricInformationDetailAdd in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	/**
	 * End adding part
	 */

	//Bi???n ????? l??u list fabric infomation l??c edit
	private List<FabricinformationdetailModel> lstFabricinformationdetailModelForEdit = new ArrayList<FabricinformationdetailModel>();
	
	/**
	 * This function is used to get fabric infomation by fabric no for editing |
	 * H??m findById fabric infomation qua fabricNo
	 * 
	 * @param fabricNo
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformation/detail/{fabricNo}", produces = "application/json", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> getFabricInformationByFabricNo(
//			@PathVariable String fabricNo) {
//		log.info(String.format("getFabricInformationByFabricNo in class %s",
//				getClass()));
//		try {
//			log.debug("getFabricInformationByFabricNo and return json");
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("status", "ok");
//			// L???y detail c???a fabric infomation
//			result.put("fabricInformation",
//					ser.findFabricinformationModelById(fabricNo));
//			// L???y list c???a n??
//			result.put("listFabricInformationDetail",
//					ser.getListFabricinformationdetailModelByFabricNo(fabricNo));
//			log.debug("getFabricInformationByFabricNo successful");
//			lstFabricinformationdetailModelForEdit = ser.getListFabricinformationdetailModelByFabricNo(fabricNo);
//			System.err.println("List fabric information detail khi edit: "+lstFabricinformationdetailModelForEdit);
//			return result;
//		} catch (Exception e) {
//			log.error(String.format(
//					"getFabricInformationByFabricNo in class %s has error: %s",
//					getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformation/detail", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFabricInformationByFabricNo(
			@RequestBody FabricinformationModel fabricinformationModel) {
		log.info(String.format("getFabricInformationByFabricNo in class %s",
				getClass()));
		try {
			log.debug("getFabricInformationByFabricNo and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			// L???y detail c???a fabric infomation
			result.put("fabricInformation",
					ser.findFabricinformationModelById(fabricinformationModel.getFabricno()));
			// L???y list c???a n??
			result.put("listFabricInformationDetail",
					ser.getListFabricinformationdetailModelByFabricNo(fabricinformationModel.getFabricno()));
			log.debug("getFabricInformationByFabricNo successful");
			lstFabricinformationdetailModelForEdit = ser.getListFabricinformationdetailModelByFabricNo(fabricinformationModel.getFabricno());
			System.err.println("List fabric information detail khi edit: "+lstFabricinformationdetailModelForEdit);
			return result;
		} catch (Exception e) {
			log.error(String.format(
					"getFabricInformationByFabricNo in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	private String fabricNoEdit= null;
	private String fabricImage= null;
	
	///
	/**
	 * This function is used to edit fabric infomation using json
	 * H??m d??ng ????? edit fabric information qua json
	 * @param fim
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fabricinformation/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> editFabricinformationJson(
			@Valid @RequestBody FabricinformationModel fim,
			BindingResult bindingResult) {
		log.info(String.format(
				"addNewFabricinformation with params 'fm' in class: %s",
				getClass()));
		try {
			log.debug("editFabricinformationJson and return status as json format");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			result.put("editStatus",ser.editFabricInformationJson(fim,lstFabricinformationdetailModelForEdit,"admin"));
			//test update h??nh
			if(fabricNoEdit!=null&&fabricImage!=null){
				ser.updateImageAfterAddNewFabricInformation(
						fabricNoEdit, fabricImage);
				fabricNoEdit= null;
				fabricImage= null;
			}
			return result;
		} catch (Exception e) {
			log.error(String
					.format("addNewFabricinformation with params 'fm' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * This function is used to edit fabric infomation
	 * | H??m d??ng ????? edit fabric infomation (c???p nh???t ???nh khi post v???)
	 * @param request
	 * @param fim
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/fabricinformation/editPost", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> editPostFabricInformation(
			HttpServletRequest request, FabricinformationModel fim) {
		Map<String, String> resultMap = new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("fabricImageEdit");

		// resultMap.put("path", "choriGarmentStyleImage/" +
		// multipartFile.getOriginalFilename());
		String name = multipartFile.getOriginalFilename();
		String handleFileName = "";

		// Th?? m???c s??? l??u
//		File dir = new File(request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage("fabricInformationLocation",
//								null, null)));
		
		File dir = new File(messageSource.getMessage("UPLOAD_FILE",
								null, null));
		
		if (!dir.exists()) {
			dir.mkdirs();
		}

		handleFileName = name.substring(0, name.lastIndexOf("."))
				+ fim.getFabricno() + new Date().getTime()
				+ name.substring(name.lastIndexOf("."));
		
//		String path = request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage("fabricInformationLocation",
//								null, null))
//				+ handleFileName;
		
		String path = messageSource.getMessage("UPLOAD_FILE",
								null, null)+ File.separator + handleFileName;
		
		System.out.println(path);
		File file = new File(path);
		try {
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			ser.updateImageAfterAddNewFabricInformation(
					fim.getFabricno(), handleFileName);
			System.err.println("T??n file ???nh: "+ handleFileName);
			//g??n ????? l??u b??n kia
			fabricNoEdit = fim.getFabricno();
			fabricImage = handleFileName;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultMap;
	}
	///
	
	/**
	 * This function is used to clear lstFabricinformationdetailModelForEdit after close edit fabric infor dialog
	 * H??m d??ng ????? clear lstFabricinformationdetailModelForEdit sau khi ????ng edit fabric infor dialog
	 * @return
	 */
	@RequestMapping(value = "/fabricinformation/clearDataAfterCloseEditFabInforDialog", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> clearDataAfterCloseEditFabInforDialog() {
		log.info(String.format(
				"clearDataAfterCloseAddFabInforDialog in class %s", getClass()));
		try {
			log.debug("getting list of All Fabric information and return json");
			Map<String, Object> result = new HashMap<String, Object>();
//			fabricInfoAddModel = null;
//			fabricinformationImg = null;
			lstFabricinformationdetailModelForEdit.clear();
			result.put("status", "ok");
			result.put("deleteStatus", true);
			log.debug("clearDataAfterCloseAddFabInforDialog successful");
			return result;
		} catch (Exception e) {
			log.error(String
					.format("clearDataAfterCloseAddFabInforDialog in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * B???t ?????u ph???n add fabric infor detail cho edit ver
	 */
	///////////////////////////////////
	/**
	 * h??m add m???i fabric infor detail khi edit
	 * @param fidm
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fabricinformationDetail/addEditVer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> addNewFabricinformationDetailEditVer(
			@Valid @RequestBody FabricinformationdetailModel fidm,
			BindingResult bindingResult) {
		log.info(String.format(
				"addNewFabricinformationDetailEditVer with params 'fm' in class: %s",
				getClass()));

		try {
			log.debug("addNewFabricinformationDetailEditVer and return status as json format");

			// ki???m tra xem c?? ch??a? ch??a c?? th?? add, c?? th?? update
			boolean checkContain = false;

			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModelForEdit) {
				// tr?????ng h???p c?? r???i
				if (fidm.getColorcode().equals(
						fabricinformationdetailModel.getColorcode())) {
					checkContain = true;
					// //n???u c?? r???i th?? update lu??n
					// fabricinformationdetailModel.setColorcode(fidm.getColorcode());
					// fabricinformationdetailModel.setYardinbl(fidm.getYardinbl());
					// fabricinformationdetailModel.setUnitprice(fidm.getUnitprice());
					// break;
				}
				if (checkContain == true) {
					// n???u c?? r???i th?? update lu??n
					fabricinformationdetailModel.setColorcode(fidm
							.getColorcode());
					fabricinformationdetailModel
							.setYardinbl(fidm.getYardinbl());
					fabricinformationdetailModel.setUnitprice(fidm
							.getUnitprice());
					break;
				}
			}

			// n???u ch??a c?? th?? add
			if (checkContain == false) {
				fidm.setColorName(colorService.findById(fidm.getColorcode())
						.getDescription());
				lstFabricinformationdetailModelForEdit.add(fidm);
			}
			//

			System.err.println("Json: " + fidm);

			System.err.println("Json lstFabricinformationdetailModel : "
					+ lstFabricinformationdetailModelForEdit);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			log.debug("addNewFabricinformation successfully");
			return result;
		} catch (Exception e) {
			log.error(String
					.format("addNewFabricinformationDetail with params 'fm' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * This function is used to save image for fabric infor detail edit ver
	 * H??m save ???nh cho fabric infor detail edit ver
	 * @param request
	 * @param fidm
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "fabricinformationDetail/addNewEditVer", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> saveImageFabricinformationDetailEditVer(
			HttpServletRequest request, FabricinformationdetailModel fidm) {
		Map<String, String> resultMap = new HashMap<String, String>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest
				.getFile("fabricImageDetailEditVer");

		String name = multipartFile.getOriginalFilename();
		String handleFileName = "";

		// N???u th?? m???c ch??a t???o th?? t???o
//		File dir = new File(request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage(
//								"fabricInformationDetailLocation", null, null)));
		
		File dir = new File(
						messageSource.getMessage(
								"UPLOAD_FILE", null, null));
		
		if (!dir.exists()) {
			dir.mkdirs();
		}

		handleFileName = name.substring(0, name.lastIndexOf("."))
				+ fidm.getColorcode() + new Date().getTime()
				+ name.substring(name.lastIndexOf("."));

//		String path = request
//				.getSession()
//				.getServletContext()
//				.getRealPath(
//						messageSource.getMessage(
//								"fabricInformationDetailLocation", null, null))
//				+ handleFileName;
		
		String path = messageSource.getMessage(
								"UPLOAD_FILE", null, null) + File.separator + handleFileName;
		
		System.out.println(path);
		File file = new File(path);
		try {
			// ki???m tra xem c?? ch??a? ch??a c?? th?? add, c?? th?? update
			boolean checkContain = false;

			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModelForEdit) {
				// tr?????ng h???p c?? r???i
				if (fidm.getColorcode().equals(
						fabricinformationdetailModel.getColorcode())) {
					checkContain = true;
					// //n???u c?? r???i th?? update lu??n
					// fabricinformationdetailModel.setColorcode(fidm.getColorcode());
					// fabricinformationdetailModel.setImgurl(handleFileName);
					// break;
				}
				if (checkContain == true) {
					// n???u c?? r???i th?? update lu??n
					fabricinformationdetailModel.setColorcode(fidm
							.getColorcode());
					fabricinformationdetailModel.setImgurl(handleFileName);
					break;
				}
			}

			// n???u ch??a c?? th?? add
			if (checkContain == false) {
				fidm.setImgurl(handleFileName);
				fidm.setColorName(colorService.findById(fidm.getColorcode())
						.getDescription());
				lstFabricinformationdetailModelForEdit.add(fidm);
			}
			// Cu???i c??ng save file
			FileCopyUtils.copy(multipartFile.getBytes(), file);
			System.err.println("Post: " + fidm);

			System.err.println("Post lstFabricinformationdetailModel : "
					+ lstFabricinformationdetailModelForEdit);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultMap;
	}
	///////////////////////////////////
	/**
	 * ------------End ph???n add fabric infor detail cho edit ver
	 */
	
	/**
	 * ---- L???y list fabric information detail khi edit
	 */
	/**
	 * This function is used to get list fabric information Detail when editing
	 * L???y list fabric information detail khi edit
	 * @return
	 */
	@RequestMapping(value = "/fabricinformationDetailEditVer/list", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllFabricinformationDetailEditVer() {
		log.info(String.format("getAllFabricinformationDetailAdd in class %s",
				getClass()));
		try {
			log.debug("getting list of All Fabric information detail and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");

			// Test
			int lstLength = lstFabricinformationdetailModelForEdit.size();
			boolean breakFlag = false;

			int index = -1;

			for (int i = 0; i < lstLength - 1; i++) {
				for (int j = i + 1; j < lstLength; j++) {
					if (lstFabricinformationdetailModelForEdit
							.get(i)
							.getColorcode()
							.equals(lstFabricinformationdetailModelForEdit.get(j)
									.getColorcode())) {
						index = j;
						breakFlag = true;
						break;
					}
				}
				if (breakFlag)
					break;
			}

			if (index > -1) {
				if (lstFabricinformationdetailModelForEdit.get(index).getImgurl() != null) {
					lstFabricinformationdetailModelForEdit.get(index - 1).setImgurl(
							lstFabricinformationdetailModelForEdit.get(index)
									.getImgurl());
				}

				if (lstFabricinformationdetailModelForEdit.get(index).getUnitprice() != null) {
					lstFabricinformationdetailModelForEdit.get(index - 1)
							.setUnitprice(
									lstFabricinformationdetailModelForEdit.get(index)
											.getUnitprice());
				}

				if (lstFabricinformationdetailModelForEdit.get(index).getYardinbl() != null) {
					lstFabricinformationdetailModelForEdit.get(index - 1).setYardinbl(
							lstFabricinformationdetailModelForEdit.get(index)
									.getYardinbl());
				}

				lstFabricinformationdetailModelForEdit.remove(index);
			}
			// End Test

			result.put("listFabricinformationDetailEditVer",
					lstFabricinformationdetailModelForEdit);
			log.debug("getAllFabricinformationDetailAdd successful");

			System.err.println("LstFabricinformationdetailModel : "
					+ lstFabricinformationdetailModelForEdit);

			return result;
		} catch (Exception e) {
			log.error(String
					.format("getAllFabricinformationDetailAdd in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	/**
	 * -----
	 */
	
	/**
	 * This function is used to delete fabric information detail of list edit H??m
	 * d??ng ????? x??a 1 ph???n t??? trong list fabric information detail c???a fabric trc
	 * khi edit
	 * 
	 * @param colorcode
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformationDetail/deleteEditVer/{colorcode}", produces = "application/json", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> deleteFabricInformationDetailEditVer(
//			@PathVariable String colorcode) {
//		log.info(String.format("deleteFabricInformationDetailAdd in class %s",
//				getClass()));
//		try {
//			log.debug("deleteFabricInformationDetailAdd and return json");
//			Map<String, Object> result = new HashMap<String, Object>();
//			// List<FactoryModel> ls = ser.getAllFactoryModel();
//			result.put("status", "ok");
//			// l???p qua list Fabric information detail Model, n???u color code =
//			// th?? x??a
//			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModelForEdit) {
//				if (fabricinformationdetailModel.getColorcode().equals(
//						colorcode)) {
//					lstFabricinformationdetailModelForEdit
//							.remove(fabricinformationdetailModel);
//					break;
//				}
//			}
//			result.put("deleteStatus", true);
//			log.debug("deleteFabricInformationDetailAdd successful");
//			return result;
//		} catch (Exception e) {
//			log.error(String
//					.format("deleteFabricInformationDetailAdd in class %s has error: %s",
//							getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformationDetail/deleteEditVer", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFabricInformationDetailEditVer(
			@RequestBody FabricinformationdetailModel fabricinformationdetailModelParam) {
		log.info(String.format("deleteFabricInformationDetailAdd in class %s",
				getClass()));
		try {
			log.debug("deleteFabricInformationDetailAdd and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			// List<FactoryModel> ls = ser.getAllFactoryModel();
			result.put("status", "ok");
			// l???p qua list Fabric information detail Model, n???u color code =
			// th?? x??a
			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModelForEdit) {
				if (fabricinformationdetailModel.getColorcode().equals(
						fabricinformationdetailModelParam.getColorcode())) {
					lstFabricinformationdetailModelForEdit
							.remove(fabricinformationdetailModel);
					break;
				}
			}
			result.put("deleteStatus", true);
			log.debug("deleteFabricInformationDetailAdd successful");
			return result;
		} catch (Exception e) {
			log.error(String
					.format("deleteFabricInformationDetailAdd in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * This function return a fabric information detail as json to send into
	 * view (find by id) for editing a fabric information detail before add H??m
	 * tr??? v??? 1 fabric information detail khi nh???n ch???n edit 1 p.t??? ????? edit khi
	 * ch???n drop down list
	 * 
	 * @param colorcode
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformationDetail/detailEditVer/{colorcode}", produces = "application/json", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> get1FabricInformationDetailEditVer(
//			@PathVariable String colorcode) {
//		log.info(String.format("get1FabricInformationDetailAdd in class %s",
//				getClass()));
//		try {
//			log.debug("get1FabricInformationDetailAdd and return json");
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("status", "ok");
//			
//			//n???u ch??a c?? trong list th?? kh???i t???o m???i unit price, yar in bl = 0
//			FabricinformationdetailModel fabInforDetailModel = new FabricinformationdetailModel();
//			fabInforDetailModel.setColorcode(colorcode);
//			fabInforDetailModel.setUnitprice((float)0);
//			fabInforDetailModel.setYardinbl((double)0);
//			result.put("fabricinformationdetailModel",
//					fabInforDetailModel);
//			// l???p qua list Fabric information detail Model, n???u color code
//			// gi???ng th?? tr??? v??? ????? ????a l??n view 1 Fabric information detail
//			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModelForEdit) {
//				if (fabricinformationdetailModel.getColorcode().equals(
//						colorcode)) {
//					result.put("fabricinformationdetailModel",
//							fabricinformationdetailModel);
//					break;
//				}
//			}
//
//			log.debug("get1FabricInformationDetailAdd successful");
//			return result;
//		} catch (Exception e) {
//			log.error(String.format(
//					"get1FabricInformationDetailAdd in class %s has error: %s",
//					getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformationDetail/detailEditVer", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get1FabricInformationDetailEditVer(
			@RequestBody FabricinformationdetailModel fabricinformationdetailModelParam) {
		log.info(String.format("get1FabricInformationDetailAdd in class %s",
				getClass()));
		try {
			log.debug("get1FabricInformationDetailAdd and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "ok");
			
			//n???u ch??a c?? trong list th?? kh???i t???o m???i unit price, yar in bl = 0
			FabricinformationdetailModel fabInforDetailModel = new FabricinformationdetailModel();
			fabInforDetailModel.setColorcode(fabricinformationdetailModelParam.getColorcode());
			fabInforDetailModel.setUnitprice((float)0);
			fabInforDetailModel.setYardinbl((double)0);
			result.put("fabricinformationdetailModel",
					fabInforDetailModel);
			// l???p qua list Fabric information detail Model, n???u color code
			// gi???ng th?? tr??? v??? ????? ????a l??n view 1 Fabric information detail
			for (FabricinformationdetailModel fabricinformationdetailModel : lstFabricinformationdetailModelForEdit) {
				if (fabricinformationdetailModel.getColorcode().equals(
						fabricinformationdetailModelParam.getColorcode())) {
					result.put("fabricinformationdetailModel",
							fabricinformationdetailModel);
					break;
				}
			}

			log.debug("get1FabricInformationDetailAdd successful");
			return result;
		} catch (Exception e) {
			log.error(String.format(
					"get1FabricInformationDetailAdd in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
	
	/**
	 * -----------------End edit part
	 */

	/**
	 * This function is used to delete a fabric information
	 * 
	 * @param fabricNo
	 * @return
	 */
//	@RequestMapping(value = "/fabricinformation/delete/{fabricNo}", produces = "application/json", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> deleteFabricInformation(
//			@PathVariable String fabricNo) {
//		log.info(String.format("deleteFabricInformation in class %s",
//				getClass()));
//		try {
//			log.debug("deleteFabricInformation and return json");
//			Map<String, Object> result = new HashMap<String, Object>();
//			// List<FactoryModel> ls = ser.getAllFactoryModel();
//			result.put("status", "ok");
//			result.put("deleteStatus", ser.deleteFabInfo(fabricNo));
//			log.debug("deleteFabricInformation successful");
//			return result;
//		} catch (Exception e) {
//			log.error(String.format(
//					"deleteFabricInformation in class %s has error: %s",
//					getClass(), e.getMessage()));
//			throw e;
//		}
//	}
	
	@RequestMapping(value = "/fabricinformation/delete", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFabricInformation(
			@RequestBody FabricinformationModel fim) {
		log.info(String.format("deleteFabricInformation in class %s",
				getClass()));
		try {
			log.debug("deleteFabricInformation and return json");
			Map<String, Object> result = new HashMap<String, Object>();
			// List<FactoryModel> ls = ser.getAllFactoryModel();
			result.put("status", "ok");
			result.put("deleteStatus", ser.deleteFabInfo(fim.getFabricno()));
			log.debug("deleteFabricInformation successful");
			return result;
		} catch (Exception e) {
			log.error(String.format(
					"deleteFabricInformation in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
}
