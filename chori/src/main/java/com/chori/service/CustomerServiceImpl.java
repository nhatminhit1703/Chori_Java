package com.chori.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chori.AbstractDao;
import com.chori.AbstractServiceImpl;
import com.chori.dao.BrandDao;
import com.chori.dao.CustomerDao;
import com.chori.dao.CustomeraccountinformationDao;
import com.chori.dao.CustomercontactDao;
import com.chori.dao.UserDao;
import com.chori.entity.Brand;
import com.chori.entity.Customer;
import com.chori.entity.Customeraccountinformation;
import com.chori.entity.Customercontact;
import com.chori.model.BrandModel;
import com.chori.model.CustomerModel;
import com.chori.model.CustomeraccountinformationModel;
import com.chori.model.CustomercontactModel;

@Repository("customerService")
public class CustomerServiceImpl extends AbstractServiceImpl<Customer, String>
		implements CustomerService {

	private CustomerDao dao;

	@Autowired
	UserDao userDao;

	@Autowired
	CustomercontactDao customerContactDao;

	@Autowired
	BrandDao brandDao;

	@Autowired
	CustomeraccountinformationDao cusaccountinfoDao;

	public CustomerServiceImpl() {
	}

	@Autowired
	public CustomerServiceImpl(
			@Qualifier("customerDao") AbstractDao<Customer, String> abstractDao) {
		super(abstractDao);
		this.dao = (CustomerDao) abstractDao;
	}

	public List<CustomerModel> getAllCustomerModel() {
		log.info(String.format("getAllCustomerEntiry in class: %s", getClass()));
		try {
			log.debug("get all Customer in DB after that return a list CustomerEntity");
			List<Customer> lstCustomer = dao.getAll();

			CustomerModel en;
			List<CustomerModel> lst = new ArrayList<CustomerModel>();

			CustomercontactModel cusContactEn;
			BrandModel brandModel;

			for (Customer cus : lstCustomer) {

				en = new CustomerModel();
				en.setCustomercode(cus.getCustomercode());
				en.setShortname(cus.getShortname());
				// en.setLongname(cus.getLongname());
				en.setAddress(cus.getAddress());
				en.setTel(cus.getTel());
				en.setFax(cus.getFax());
				en.setTaxno(cus.getTaxno());
				en.setRemark(cus.getRemark());
				en.setCreatedate(cus.getCreatedate() == null ? null : cus
						.getCreatedate());
				en.setStatus(cus.getStatus() == null ? "" : cus.getStatus());
				en.setCreator(cus.getUser() == null ? "" : cus.getUser()
						.getUsername());

				// l????????????y ra cuscontact c????????????a customer
				Set<Customercontact> lstCusContact = cus.getCustomercontacts();
				Set<Brand> lstBrand = cus.getBrands();
				for (Customercontact customercontact : lstCusContact) {
					cusContactEn = new CustomercontactModel();
					cusContactEn.setCustomerCode(customercontact.getCustomer()
							.getCustomercode());
					cusContactEn.setCustomercontactcode(customercontact
							.getCuscontactcode());
					cusContactEn.setName(customercontact.getName());
					cusContactEn.setEmail(customercontact.getEmail());
					cusContactEn.setTel(customercontact.getTel());
					cusContactEn.setCreator(customercontact.getUser()
							.getUsername());
					cusContactEn.setCreatedate(customercontact.getCreatedate());
					//

					en.getCustomercontactModellist().add(cusContactEn);
				}
				for (Brand brand : lstBrand) {
					brandModel = new BrandModel();
					brandModel.setCustomerCode(brand.getCustomer()
							.getCustomercode());
					brandModel.setBrandcode(brand.getBrandcode());
					brandModel.setBrandname(brand.getBrandname());
					brandModel.setCreator(brand.getUser().getUsername());
					brandModel.setCreatedate(brand.getCreatedate());
					//

					en.getBrandModellist().add(brandModel);
				}

				lst.add(en);
			}
			log.debug("getAllCustomerEntiry successfully");
			return lst;
		} catch (Exception e) {
			log.error(String.format(
					"getAllCustomerEntiry in class: %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public boolean addNewCustomer(CustomerModel cm, String userName) {

		Customer en = new Customer();
		en.setCustomercode(cm.getCustomercode());
		en.setUser(userDao.findById(userName));
		en.setShortname(cm.getShortname());
		// en.setLongname(cm.getLongname());
		en.setAddress(cm.getAddress());
		en.setTel(cm.getTel());
		en.setFax(cm.getFax());
		en.setTaxno(cm.getTaxno());
		en.setStatus(cm.getStatus());
		en.setRemark(cm.getRemark());
		en.setCreatedate(new Date());

		try {
			// save customer v????o csdl trc sau ????????? m??????y contact m???????i c???? id
			dao.save(en);
			System.err.println("Save customer th????nh c????ng");
		} catch (Exception e) {
			System.err.println("Save customer ko th????nh c????ng");
			return false;
		}

		// T??????o bi??????n ??????????? l????u t??????ng customercontact t?????? param
		Customercontact cusContact;

		Set<CustomercontactModel> newList = cm.getCustomercontactModellist();
		for (CustomercontactModel customercontactModel : newList) {
			cusContact = new Customercontact();
			cusContact.setCustomer(dao.findById(cm.getCustomercode()));
			cusContact.setUser(userDao.findById(userName));
			cusContact.setName(customercontactModel.getName());
			cusContact.setEmail(customercontactModel.getEmail());
			cusContact.setTel(customercontactModel.getTel());
			cusContact.setCreatedate(new Date());

			customerContactDao.save(cusContact);
		}

		Brand brand;

		Set<BrandModel> brandList = cm.getBrandModellist();
		for (BrandModel brandModel : brandList) {
			brand = new Brand();
			brand.setCustomer(dao.findById(cm.getCustomercode()));
			brand.setUser(userDao.findById(userName));
			brand.setBrandname(brandModel.getBrandname());
			brand.setCreatedate(new Date());

			brandDao.save(brand);
		}

		Customeraccountinformation customeraccountinformation;

		Set<CustomeraccountinformationModel> lstCustomeraccountinformation = cm
				.getCustomeraccountinformationModellist();
		for (CustomeraccountinformationModel customeraccountinformationModel : lstCustomeraccountinformation) {
			customeraccountinformation = new Customeraccountinformation();
			customeraccountinformation.setCustomer(dao.findById(cm
					.getCustomercode()));
			customeraccountinformation.setUser(userDao.findById(userName));
			customeraccountinformation
					.setBankname(customeraccountinformationModel.getBankname());
			customeraccountinformation
					.setBankbranch(customeraccountinformationModel
							.getBankbranch());
			customeraccountinformation
					.setAccountnumber(customeraccountinformationModel
							.getAccountnumber());
			customeraccountinformation
					.setAddress(customeraccountinformationModel.getAddress());
			customeraccountinformation
					.setSwiftcode(customeraccountinformationModel
							.getSwiftcode());
			customeraccountinformation.setCreatedate(new Date());

			cusaccountinfoDao.save(customeraccountinformation);
		}

		System.err.println("Save c????c customerContact th????nh c????ng");
		return true;
	}

	@Override
	public CustomerModel findCustomerModelById(String id) {
		Customer cus = dao.findById(id);
		CustomerModel cm = new CustomerModel();

		cm.setCustomercode(cus.getCustomercode());
		cm.setCreator(cus.getUser().getUsername());
		cm.setShortname(cus.getShortname());
		// cm.setLongname(cus.getLongname());
		cm.setAddress(cus.getAddress());
		cm.setTel(cus.getTel());
		cm.setFax(cus.getFax());
		cm.setTaxno(cus.getTaxno());
		cm.setStatus(cus.getStatus());
		cm.setRemark(cus.getRemark());
		cm.setCreatedate(cus.getCreatedate());

		CustomercontactModel ccm;
		Set<Customercontact> lst = cus.getCustomercontacts();
		for (Customercontact customercontact : lst) {
			ccm = new CustomercontactModel();
			ccm.setCustomercontactcode(customercontact.getCuscontactcode());
			ccm.setCustomerCode(customercontact.getCustomer().getCustomercode());
			ccm.setCreator(customercontact.getUser().getUsername());
			ccm.setName(customercontact.getName());
			ccm.setEmail(customercontact.getEmail());
			ccm.setTel(customercontact.getTel());
			ccm.setCreatedate(customercontact.getCreatedate());

			cm.getCustomercontactModellist().add(ccm);
		}

		BrandModel brandModel;
		Set<Brand> listBrand = cus.getBrands();
		for (Brand brand : listBrand) {
			brandModel = new BrandModel();
			brandModel.setCustomerCode(brand.getCustomer().getCustomercode());
			brandModel.setBrandcode(brand.getBrandcode());
			brandModel.setBrandname(brand.getBrandname());
			brandModel.setCreator(brand.getUser().getUsername());
			brandModel.setCreatedate(brand.getCreatedate());

			cm.getBrandModellist().add(brandModel);
		}

		CustomeraccountinformationModel customeraccountinformationModel;
		Set<Customeraccountinformation> listCustomeraccountinformation = cus
				.getCustomeraccountinformations();
		for (Customeraccountinformation customeraccountinformation : listCustomeraccountinformation) {
			customeraccountinformationModel = new CustomeraccountinformationModel();
			customeraccountinformationModel
					.setCustomerCode(customeraccountinformation.getCustomer()
							.getCustomercode());
			customeraccountinformationModel
					.setCustomeraccountinfocode(customeraccountinformation
							.getCustomeraccountinfocode());
			customeraccountinformationModel
					.setBankname(customeraccountinformation.getBankname());
			customeraccountinformationModel
					.setBankbranch(customeraccountinformation.getBankbranch());
			customeraccountinformationModel
					.setAccountnumber(customeraccountinformation
							.getAccountnumber());
			customeraccountinformationModel
					.setAddress(customeraccountinformation.getAddress());
			customeraccountinformationModel
					.setSwiftcode(customeraccountinformation.getSwiftcode());

			cm.getCustomeraccountinformationModellist().add(
					customeraccountinformationModel);
		}

		return cm;
	}

	@Override
	public boolean editCustomer(CustomerModel cm, String userName) {
		Customer cus = dao.findById(cm.getCustomercode());
		cus.setShortname(cm.getShortname());
		// cus.setLongname(cm.getLongname());
		cus.setAddress(cm.getAddress());
		cus.setTel(cm.getTel());
		cus.setFax(cm.getFax());
		cus.setTaxno(cm.getTaxno());
		cus.setStatus(cm.getStatus());
		cus.setRemark(cm.getRemark());
		// fac.setCreatedate(new Date());

		try {
			// save customer v????o csdl trc sau ????????? m??????y contact m???????i c???? id
			dao.update(cus);
			System.err.println("Edit customer th????nh c????ng");
		} catch (Exception e) {
			System.err.println("Edit customer ko th????nh c????ng");
			return false;
		}

		Set<CustomercontactModel> newList = cm.getCustomercontactModellist();
		Set<Customercontact> oldList = cus.getCustomercontacts();
		Set<BrandModel> newBrandList = cm.getBrandModellist();
		Set<Brand> oldBrandList = cus.getBrands();
		Set<CustomeraccountinformationModel> newCusAccInfo = cm
				.getCustomeraccountinformationModellist();
		Set<Customeraccountinformation> oldCusAccInfo = cus
				.getCustomeraccountinformations();

		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;

		// tr???????????ng h??????p list c???? ==0
		if (oldList.size() == 0) {
			if (newList.size() == 0) {
				// return true;//2 list r???????ng nh???? nhau th???? ko l????m g????
				flag1 = true;
			} else {// n??????u list m???????i c???? contact, th???? add h??????t list m???????i
					// ????????? v????o
				Customercontact cusContact;

				for (CustomercontactModel customercontactModel : newList) {
					cusContact = new Customercontact();
					cusContact.setCustomer(dao.findById(cm.getCustomercode()));
					cusContact.setUser(userDao.findById(userName));
					cusContact.setName(customercontactModel.getName());
					cusContact.setEmail(customercontactModel.getEmail());
					cusContact.setTel(customercontactModel.getTel());
					cusContact.setCreatedate(new Date());

					customerContactDao.save(cusContact);
				}
				// return true;
				flag1 = true;
			}
		} else {// Tr???????????ng h??????p list c???? ko r???????ng
			if (newList.size() == 0) {// v???? list m???????i r???????ng, th???? del h??????t
										// contact
				for (Customercontact customercontact : oldList) {
					customerContactDao.delete(customerContactDao
							.findById(customercontact.getCuscontactcode()));
				}
				// return true;
				flag1 = true;
			} else {// list m???????i kh????ng r???????ng

				// l???????c qua list m???????i, customercontactcode ==0 th???? th????m
				// m???????i
				Customercontact cusContact;

				for (CustomercontactModel customercontactModel : newList) {
					if (customercontactModel.getCustomercontactcode() == 0) {
						cusContact = new Customercontact();
						cusContact.setCustomer(dao.findById(cm
								.getCustomercode()));
						cusContact.setUser(userDao.findById(userName));
						cusContact.setName(customercontactModel.getName());
						cusContact.setEmail(customercontactModel.getEmail());
						cusContact.setTel(customercontactModel.getTel());
						cusContact.setCreatedate(new Date());

						customerContactDao.save(cusContact);
					}
				}

				// true l???? t???????n t??????i c?????? 2 list, false l???? ko t???????n t??????i
				// trong list m???????i
				boolean flag = false;

				// T??????o list Customercontact ??????????? remove
				List<Customercontact> lstToBeRemove = new ArrayList<Customercontact>();
				List<CustomercontactModel> lstToBeUpdate = new ArrayList<CustomercontactModel>();

				for (CustomercontactModel customercontactModel : newList) {
					for (Customercontact customercontact : oldList) {
						// n??????u contact trong list c???? ko t???????n t??????i trong
						// list m???????i, n??????u contact code m???? = nhau th???? l????
						// c???? t???????n t??????i, g????n flag = true
						if (customercontact.getCuscontactcode() == customercontactModel
								.getCustomercontactcode()) {
							flag = true;
							break;
						}
					}
					if (flag == true) {
						lstToBeUpdate.add(customercontactModel);
						flag = false;// set l??????i th????nh ko t???????n t??????i
					}
				}

				// L???????c ra nh??????ng contact ko t???????n t??????i trong list m???????i
				flag = false;// m??????c ????????????nh l???? ko t???????n t??????i
				for (Customercontact customercontact : oldList) {
					for (CustomercontactModel customercontactModel : newList) {
						if (customercontact.getCuscontactcode() == customercontactModel
								.getCustomercontactcode()) {
							flag = true;
							break;
						}
					}

					if (flag == false) {
						lstToBeRemove.add(customercontact);
					}
					flag = false;
				}

				// l??????p qua list c??????n remove
				for (Customercontact customercontact : lstToBeRemove) {
					customerContactDao.delete(customerContactDao
							.findById(customercontact.getCuscontactcode()));
				}

				Customercontact ccNeedUpdate;
				// l??????p qua list c??????n update
				for (CustomercontactModel customercontactModel : lstToBeUpdate) {
					ccNeedUpdate = customerContactDao
							.findById(customercontactModel
									.getCustomercontactcode());

					ccNeedUpdate.setName(customercontactModel.getName());
					ccNeedUpdate.setEmail(customercontactModel.getEmail());
					ccNeedUpdate.setTel(customercontactModel.getTel());

					customerContactDao.update(ccNeedUpdate);
				}

				// return true;
				flag1 = true;
			}
		}

		// Brand

		if (oldBrandList.size() == 0) {
			if (newBrandList.size() == 0) {
				// return true;//2 list r???????ng nh???? nhau th???? ko l????m g????
				flag2 = true;
			} else {// n??????u list m???????i c???? contact, th???? add h??????t list m???????i
					// ????????? v????o
				Brand brand;

				for (BrandModel brandModel : newBrandList) {
					brand = new Brand();
					brand.setCustomer(dao.findById(cm.getCustomercode()));
					brand.setUser(userDao.findById(userName));
					brand.setBrandname(brandModel.getBrandname());
					brand.setCreatedate(new Date());

					brandDao.save(brand);
				}
				// return true;
				flag2 = true;
			}
		} else {// Tr???????????ng h??????p list c???? ko r???????ng
			if (newBrandList.size() == 0) {// v???? list m???????i r???????ng, th???? del
											// h??????t contact
				for (Brand brand : oldBrandList) {
					brandDao.delete(brandDao.findById(brand.getBrandcode()));
				}
				return true;
			} else {// list m???????i kh????ng r???????ng

				// l???????c qua list m???????i, customercontactcode ==0 th???? th????m
				// m???????i
				Brand brand;

				for (BrandModel brandModel : newBrandList) {
					if (brandModel.getBrandcode() == 0) {
						brand = new Brand();
						brand.setCustomer(dao.findById(cm.getCustomercode()));
						brand.setUser(userDao.findById(userName));
						brand.setBrandname(brandModel.getBrandname());
						brand.setCreatedate(new Date());

						brandDao.save(brand);
					}
				}

				// true l???? t???????n t??????i c?????? 2 list, false l???? ko t???????n t??????i
				// trong list m???????i
				boolean flag = false;

				// T??????o list Customercontact ??????????? remove
				List<Brand> lstToBeRemove = new ArrayList<Brand>();
				List<BrandModel> lstToBeUpdate = new ArrayList<BrandModel>();

				for (BrandModel brandModel : newBrandList) {
					for (Brand brand2 : oldBrandList) {
						// n??????u contact trong list c???? ko t???????n t??????i trong
						// list m???????i, n??????u contact code m???? = nhau th???? l????
						// c???? t???????n t??????i, g????n flag = true
						if (brand2.getBrandcode() == brandModel.getBrandcode()) {
							flag = true;
							break;
						}
					}
					if (flag == true) {
						lstToBeUpdate.add(brandModel);
						flag = false;// set l??????i th????nh ko t???????n t??????i
					}
				}

				// L???????c ra nh??????ng contact ko t???????n t??????i trong list m???????i
				flag = false;// m??????c ????????????nh l???? ko t???????n t??????i
				for (Brand brand2 : oldBrandList) {
					for (BrandModel brandModel : newBrandList) {
						if (brand2.getBrandcode() == brandModel.getBrandcode()) {
							flag = true;
							break;
						}
					}

					if (flag == false) {
						lstToBeRemove.add(brand2);
					}
					flag = false;
				}

				// l??????p qua list c??????n remove
				for (Brand brand2 : lstToBeRemove) {
					brandDao.delete(brandDao.findById(brand2.getBrandcode()));
				}

				Brand ccNeedUpdate;
				// l??????p qua list c??????n update
				for (BrandModel brandModel : lstToBeUpdate) {
					ccNeedUpdate = brandDao.findById(brandModel.getBrandcode());

					ccNeedUpdate.setBrandname(brandModel.getBrandname());

					brandDao.update(ccNeedUpdate);
				}

				// return true;
				flag2 = true;
			}
		}

		if (oldCusAccInfo.size() == 0) {
			if (newCusAccInfo.size() == 0) {
				// return true;// 2 list r???????ng nh???? nhau th???? ko l????m g????
				flag3 = true;
			} else {// n??????u list m???????i c???? contact, th???? add h??????t list m???????i
					// ????????? v????o
				Customeraccountinformation customeraccountinformation;

				for (CustomeraccountinformationModel customeraccountinformationModel : newCusAccInfo) {
					customeraccountinformation = new Customeraccountinformation();
					customeraccountinformation.setCustomer(dao.findById(cm
							.getCustomercode()));
					customeraccountinformation.setUser(userDao
							.findById(userName));
					customeraccountinformation
							.setBankname(customeraccountinformationModel
									.getBankname());
					customeraccountinformation
							.setBankbranch(customeraccountinformationModel
									.getBankbranch());
					customeraccountinformation
							.setAccountnumber(customeraccountinformationModel
									.getAccountnumber());
					customeraccountinformation
							.setAddress(customeraccountinformationModel
									.getAddress());
					customeraccountinformation
							.setSwiftcode(customeraccountinformationModel
									.getSwiftcode());
					customeraccountinformation.setCreatedate(new Date());

					cusaccountinfoDao.save(customeraccountinformation);
				}
				// return true;
				flag3 = true;
			}
		} else {// Tr???????????ng h??????p list c???? ko r???????ng
			if (newCusAccInfo.size() == 0) {// v???? list m???????i r???????ng, th???? del
											// h??????t
				// contact
				for (Customeraccountinformation customeraccountinformation : oldCusAccInfo) {
					cusaccountinfoDao.delete(cusaccountinfoDao
							.findById(customeraccountinformation
									.getCustomeraccountinfocode()));
				}
				// return true;
				flag3 = true;
			} else {// list m???????i kh????ng r???????ng

				// l???????c qua list m???????i, factorycontactcode ==0 th???? th????m m???????i
				Customeraccountinformation customeraccountinformation;

				for (CustomeraccountinformationModel customeraccountinformationModel : newCusAccInfo) {
					if (customeraccountinformationModel
							.getCustomeraccountinfocode() == 0) {
						customeraccountinformation = new Customeraccountinformation();
						customeraccountinformation.setCustomer(dao.findById(cm
								.getCustomercode()));
						customeraccountinformation.setUser(userDao
								.findById(userName));
						customeraccountinformation
								.setBankname(customeraccountinformationModel
										.getBankname());
						customeraccountinformation
								.setBankbranch(customeraccountinformationModel
										.getBankbranch());
						customeraccountinformation
								.setAccountnumber(customeraccountinformationModel
										.getAccountnumber());
						customeraccountinformation
								.setAddress(customeraccountinformationModel
										.getAddress());
						customeraccountinformation
								.setSwiftcode(customeraccountinformationModel
										.getSwiftcode());
						customeraccountinformation.setCreatedate(new Date());

						cusaccountinfoDao.save(customeraccountinformation);
					}
				}

				// true l???? t???????n t??????i c?????? 2 list, false l???? ko t???????n t??????i
				// trong list
				// m???????i
				boolean flag = false;

				// T??????o list Factorycontact ??????????? remove
				List<Customeraccountinformation> lstToBeRemove = new ArrayList<Customeraccountinformation>();
				List<CustomeraccountinformationModel> lstToBeUpdate = new ArrayList<CustomeraccountinformationModel>();

				for (CustomeraccountinformationModel customeraccountinformationModel : newCusAccInfo) {
					for (Customeraccountinformation customeraccountinformation2 : oldCusAccInfo) {
						// n??????u contact trong list c???? ko t???????n t??????i trong
						// list
						// m???????i, n??????u contact code m???? = nhau th???? l???? c????
						// t???????n
						// t??????i, g????n flag = true
						if (customeraccountinformation2
								.getCustomeraccountinfocode() == customeraccountinformationModel
								.getCustomeraccountinfocode()) {
							flag = true;
							break;
						}
					}
					if (flag == true) {
						lstToBeUpdate.add(customeraccountinformationModel);
						flag = false;// set l??????i th????nh ko t???????n t??????i
					}
				}

				// L???????c ra nh??????ng contact ko t???????n t??????i trong list m???????i
				flag = false;// m??????c ????????????nh l???? ko t???????n t??????i
				for (Customeraccountinformation customeraccountinformation2 : oldCusAccInfo) {
					for (CustomeraccountinformationModel customeraccountinformationModel : newCusAccInfo) {
						if (customeraccountinformation2
								.getCustomeraccountinfocode() == customeraccountinformationModel
								.getCustomeraccountinfocode()) {
							flag = true;
							break;
						}
					}
					if (flag == false) {
						lstToBeRemove.add(customeraccountinformation2);
					}
					flag = false;
				}

				// l??????p qua list c??????n remove
				for (Customeraccountinformation customeraccountinformation2 : lstToBeRemove) {
					cusaccountinfoDao.delete(cusaccountinfoDao
							.findById(customeraccountinformation2
									.getCustomeraccountinfocode()));
				}

				Customeraccountinformation fcNeedUpdate;
				// l??????p qua list c??????n update
				for (CustomeraccountinformationModel customeraccountinformationModel : lstToBeUpdate) {
					fcNeedUpdate = cusaccountinfoDao
							.findById(customeraccountinformationModel
									.getCustomeraccountinfocode());

					fcNeedUpdate.setBankname(customeraccountinformationModel
							.getBankname());
					fcNeedUpdate.setBankbranch(customeraccountinformationModel
							.getBankbranch());
					fcNeedUpdate
							.setAccountnumber(customeraccountinformationModel
									.getAccountnumber());
					fcNeedUpdate.setAddress(customeraccountinformationModel
							.getAddress());
					fcNeedUpdate.setSwiftcode(customeraccountinformationModel
							.getSwiftcode());
					// facContact.setCreatedate(new Date());

					cusaccountinfoDao.save(fcNeedUpdate);
				}

				// return true;
				flag3 = true;
			}
		}

		if (flag1 == true && flag2 == true && flag3 == true) {
			return true;
		} else {
			return false;
		}
		// return false;
	}

	// public boolean delete(String id){
	// try{
	// Customer cus= dao.findById(id);
	//
	// Set<Customercontact> lst= cus.getCustomercontacts();
	// for (Customercontact customercontact : lst) {
	// customerContactDao.delete(customerContactDao.findById(customercontact.getCuscontactcode()));
	// }
	//
	// dao.delete(cus);
	//
	// return true;
	// }catch(Exception e){
	// throw e;
	// }
	// }
	@Override
	public boolean isCustomerExistedById(String id) {
		log.info(String.format(
				"isCustomerExistedById with param 'id' in class: %s",
				getClass()));
		try {
			if (null == dao.findById(id))
				return false;
			return true;
		} catch (Exception e) {
			log.error(String
					.format("isCustomerExistedById with param 'id' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

}
