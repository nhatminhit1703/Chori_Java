package com.chori.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chori.AbstractDao;
import com.chori.AbstractServiceImpl;
import com.chori.dao.AgentDao;
import com.chori.dao.AgentcontactDao;
import com.chori.dao.UserDao;
import com.chori.entity.Agent;
import com.chori.entity.Agentcontact;
import com.chori.model.AgentModel;
import com.chori.model.AgentcontactModel;

@Service("agentService")
public class AgentServiceImpl extends AbstractServiceImpl<Agent, Integer>
		implements AgentService {
	private AgentDao dao;

	@Autowired
	UserDao userDao;

	@Autowired
	AgentcontactDao agentcontactDao;

	@Autowired
	public AgentServiceImpl(
			@Qualifier("agentDao") AbstractDao<Agent, Integer> abstractDao) {
		super(abstractDao);
		this.dao = (AgentDao) abstractDao;
	}

	/**
	 * This function is used to get a list of all agent in database
	 * 
	 * @return List<UserModel>
	 */
	public List<AgentModel> getAllAgentModel() {
		log.info(String.format("getAllAgentModel in class: %s", getClass()));
		try {
			log.debug("get all Agent in DB after that return a list agentModel");
			List<Agent> lstAgent = dao.getAll();

			AgentModel am;
			List<AgentModel> lstAgentModel = new ArrayList<AgentModel>();
			AgentcontactModel agentcontactModel;

			for (Agent a : lstAgent) {

				am = new AgentModel();
				am.setAgentcode(a.getAgentcode());
				am.setShortname(a.getShortname());
				am.setLongname(a.getLongname());
				am.setAddress(a.getAddress());
				am.setTel(a.getTel());
				am.setFax(a.getFax());
				am.setTaxno(a.getTaxno());
				am.setCreatedate(a.getCreatedate());
				am.setStatus(a.getStatus());
				am.setRemark(a.getRemark());
				am.setCreator(a.getUser() == null ? "" : a.getUser()
						.getUsername());

				Set<Agentcontact> lstAgentContact = a.getAgentcontacts();
				for (Agentcontact agentcontact : lstAgentContact) {
					agentcontactModel = new AgentcontactModel();
					agentcontactModel.setAgentCode(agentcontact.getAgent()
							.getAgentcode());
					agentcontactModel.setAgentcontactcode(agentcontact
							.getAgentcontactcode());
					agentcontactModel.setName(agentcontact.getName());
					agentcontactModel.setEmail(agentcontact.getEmail());
					agentcontactModel.setTel(agentcontact.getTel());
					agentcontactModel
							.setCreator(agentcontact.getUser() == null ? ""
									: agentcontact.getUser().getUsername());
					agentcontactModel.setCreatedate(agentcontact
							.getCreatedate());
					//

					am.getAgentcontactModellist().add(agentcontactModel);
				}

				lstAgentModel.add(am);
			}
			log.debug("getAllAgentModel successfully");
			return lstAgentModel;
		} catch (Exception e) {
			log.error(String.format(
					"getAllAgentModel in class: %s has error: %s", getClass(),
					e.getMessage()));
			throw e;
		}
	}

	@Override
	public AgentModel findAgentModelById(Integer id) {
		log.info(String.format(
				"findAgentModelById with param 'id' in class: %s", getClass()));
		try {
			AgentModel am = new AgentModel();
			Agent a = dao.findById(id);

			// field for NV
			am.setAgentcode(a.getAgentcode());
			am.setShortname(a.getShortname());
			am.setLongname(a.getLongname());
			am.setAddress(a.getAddress());
			am.setTel(a.getTel());
			am.setFax(a.getFax());
			am.setTaxno(a.getTaxno());
			am.setCreatedate(a.getCreatedate());
			am.setStatus(a.getStatus());
			am.setRemark(a.getRemark());
			am.setCreator(a.getUser() == null ? " " : a.getUser().getUsername());

			log.debug("findAgentModelById successfully");
			AgentcontactModel agentcontactModel;

			Set<Agentcontact> lstAgentcontact = a.getAgentcontacts();
			for (Agentcontact agentcontact : lstAgentcontact) {
				agentcontactModel = new AgentcontactModel();
				agentcontactModel.setAgentcontactcode(agentcontact
						.getAgentcontactcode());
				agentcontactModel.setAgentCode(agentcontact.getAgent()
						.getAgentcode());
				agentcontactModel
						.setCreator(agentcontact.getUser() == null ? " "
								: agentcontact.getUser().getUsername());
				agentcontactModel.setName(agentcontact.getName());
				agentcontactModel.setEmail(agentcontact.getEmail());
				agentcontactModel.setTel(agentcontact.getTel());
				agentcontactModel.setCreatedate(agentcontact.getCreatedate());

				am.getAgentcontactModellist().add(agentcontactModel);
			}

			return am;
		} catch (Exception e) {
			log.error(String
					.format("findAgentModelById with param 'id' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public boolean addNewAgent(AgentModel agentAddModel, String userId) {
		try {
			Agent a = new Agent();

			a.setShortname(agentAddModel.getShortname());
			a.setLongname(agentAddModel.getLongname());
			a.setAddress(agentAddModel.getAddress());
			a.setTel(agentAddModel.getTel());
			a.setFax(agentAddModel.getFax());
			a.setTaxno(agentAddModel.getTaxno());
			a.setRemark(agentAddModel.getRemark());
			a.setStatus(agentAddModel.getStatus());
			a.setCreatedate(new Date());
			a.setUser(userDao.findById(userId));

			dao.save(a);

			Agentcontact agentcontact;

			Set<AgentcontactModel> newList = agentAddModel
					.getAgentcontactModellist();
			for (AgentcontactModel agentcontactModel : newList) {
				agentcontact = new Agentcontact();
				agentcontact.setAgent(dao.findById(a.getAgentcode()));
				agentcontact.setUser(userDao.findById(userId));
				agentcontact.setName(agentcontactModel.getName());
				agentcontact.setEmail(agentcontactModel.getEmail());
				agentcontact.setTel(agentcontactModel.getTel());
				agentcontact.setCreatedate(new Date());

				agentcontactDao.save(agentcontact);
			}
			return true;
		} catch (Exception e) {
			log.error(String
					.format("addNewAgent with params 'agentAddModel', 'userId' in class %s has error: %s",
							getClass(), e.getMessage()));
			// return false;
			throw e;
		}
	}

	@Override
	public boolean editAgent(AgentModel agentAddModel, String userId) {
		log.info(String.format(
				"editAgent with params 'agentAddModel', 'userId' in class: %s",
				getClass()));
		try {
			Agent a = dao.findById(agentAddModel.getAgentcode());
			a.setAgentcode(agentAddModel.getAgentcode());
			a.setShortname(agentAddModel.getShortname());
			a.setLongname(agentAddModel.getLongname());
			a.setAddress(agentAddModel.getAddress());
			a.setTel(agentAddModel.getTel());
			a.setFax(agentAddModel.getFax());
			a.setTaxno(agentAddModel.getTaxno());
			a.setRemark(agentAddModel.getRemark());
			a.setStatus(agentAddModel.getStatus());
			dao.update(a);

			Set<AgentcontactModel> newList = agentAddModel
					.getAgentcontactModellist();
			Set<Agentcontact> oldList = a.getAgentcontacts();

			if (oldList.size() == 0) {
				if (newList.size() == 0) {
					return true;// 2 list r???????ng nh???? nhau th???? ko l????m g????
				} else {// n??????u list m???????i c???? contact, th???? add h??????t list
						// m???????i ????????? v????o
					Agentcontact agentcontact;

					for (AgentcontactModel agentcontactModel : newList) {
						agentcontact = new Agentcontact();
						agentcontact.setAgent(dao.findById(agentAddModel
								.getAgentcode()));
						agentcontact.setUser(userDao.findById(userId));
						agentcontact.setName(agentcontactModel.getName());
						agentcontact.setEmail(agentcontactModel.getEmail());
						agentcontact.setTel(agentcontactModel.getTel());
						agentcontact.setCreatedate(new Date());

						agentcontactDao.save(agentcontact);
					}
					return true;
				}
			} else {// Tr???????????ng h??????p list c???? ko r???????ng
				if (newList.size() == 0) {// v???? list m???????i r???????ng, th???? del
											// h??????t contact
					for (Agentcontact agentcontact : oldList) {
						agentcontactDao.delete(agentcontactDao
								.findById(agentcontact.getAgentcontactcode()));
					}
					return true;

				} else {// list m???????i kh????ng r???????ng

					// l???????c qua list m???????i, agentcontactcode ==0 th???? th????m
					// m???????i
					Agentcontact agentcontact;

					for (AgentcontactModel agentcontactModel : newList) {
						if (agentcontactModel.getAgentcontactcode() == 0) {
							agentcontact = new Agentcontact();
							agentcontact.setAgent(dao.findById(agentAddModel
									.getAgentcode()));
							agentcontact.setUser(userDao.findById(userId));
							agentcontact.setName(agentcontactModel.getName());
							agentcontact.setEmail(agentcontactModel.getEmail());
							agentcontact.setTel(agentcontactModel.getTel());
							agentcontact.setCreatedate(new Date());

							agentcontactDao.save(agentcontact);
						}
					}

					// true l???? t???????n t??????i c?????? 2 list, false l???? ko t???????n
					// t??????i trong list m???????i
					boolean flag = false;

					// T??????o list Customercontact ??????????? remove
					List<Agentcontact> lstToBeRemove = new ArrayList<Agentcontact>();
					List<AgentcontactModel> lstToBeUpdate = new ArrayList<AgentcontactModel>();

					for (AgentcontactModel agentcontactModel : newList) {
						for (Agentcontact agentcontact2 : oldList) {
							// n??????u contact trong list c???? ko t???????n t??????i trong
							// list m???????i, n??????u contact code m???? = nhau th????
							// l???? c???? t???????n t??????i, g????n flag = true
							if (agentcontact2.getAgentcontactcode() == agentcontactModel
									.getAgentcontactcode()) {
								flag = true;
								break;
							}
						}
						if (flag == true) {
							lstToBeUpdate.add(agentcontactModel);
							flag = false;// set l??????i th????nh ko t???????n t??????i
						}
					}

					// L???????c ra nh??????ng contact ko t???????n t??????i trong list m???????i
					flag = false;// m??????c ????????????nh l???? ko t???????n t??????i
					for (Agentcontact agentcontact2 : oldList) {
						for (AgentcontactModel agentcontactModel : newList) {
							if (agentcontact2.getAgentcontactcode() == agentcontactModel
									.getAgentcontactcode()) {
								flag = true;
								break;
							}
						}

						if (flag == false) {
							lstToBeRemove.add(agentcontact2);
						}
						flag = false;
					}

					// l??????p qua list c??????n remove
					for (Agentcontact agentcontact2 : lstToBeRemove) {
						agentcontactDao.delete(agentcontactDao
								.findById(agentcontact2.getAgentcontactcode()));
					}

					Agentcontact ccNeedUpdate;
					// l??????p qua list c??????n update
					for (AgentcontactModel agentcontactModel : lstToBeUpdate) {
						ccNeedUpdate = agentcontactDao
								.findById(agentcontactModel
										.getAgentcontactcode());

						ccNeedUpdate.setName(agentcontactModel.getName());
						ccNeedUpdate.setEmail(agentcontactModel.getEmail());
						ccNeedUpdate.setTel(agentcontactModel.getTel());

						agentcontactDao.update(ccNeedUpdate);
					}

					return true;
				}
			}

			// return false;
		} catch (Exception e) {
			log.error(String
					.format("editAgent with params 'agentAddModel', 'userId' in class %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	// @Override
	// public boolean isAgentExistedById(Integer id) {
	// log.info(String.format("isAgentExistedById with param 'id' in class: %s",
	// getClass()));
	// try{
	// if(null==dao.findById(id))
	// return false;
	// return true;
	// }catch(Exception e){
	// log.error(String.format("isAgentExistedById with param 'id' in class: %s has error: %s",
	// getClass(), e.getMessage()));
	// throw e;
	// }
	// }

	@Override
	public boolean isAgentExistedByShortname(String shortname) {
		log.info(String
				.format("isAgentExistedByShortname with param 'shortname' in class: %s",
						getClass()));
		try {
			List<Agent> lstAgent = dao.getAll();

			for (Agent a : lstAgent) {
				if (a.getShortname().equals(shortname))
					return true;
			}

			return false;
		} catch (Exception e) {
			log.error(String
					.format("isAgentExistedByShortname with param 'shortname' in class: %s has error: %s",
							getClass(), e.getMessage()));
			throw e;
		}
	}

	public boolean deleteAgent(Integer id) {
		log.info(String.format("delete with params 'id' in class: %s",
				getClass()));
		try {
			Agent agent = dao.findById(id);

			Set<Agentcontact> lst = agent.getAgentcontacts();
			for (Agentcontact agentcontact : lst) {
				agentcontactDao.delete(agentcontactDao.findById(agentcontact
						.getAgentcontactcode()));
			}

			dao.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error(String.format(
					"delete with params 'id' in class %s has error: %s",
					getClass(), e.getMessage()));
			throw e;
		}
	}
}
