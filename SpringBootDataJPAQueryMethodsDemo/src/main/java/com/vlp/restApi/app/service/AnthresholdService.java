package com.vlp.restApi.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vlp.restApi.app.bean.Anthreshold;
import com.vlp.restApi.app.dao.AnthresholdServiceDao;
import com.vlp.restApi.app.entities.NoasVLPInventory;
import com.vlp.restApi.app.exception.ErrorResponse;
import com.vlp.restApi.app.exception.RecordNotFoundException;

@Service
public class AnthresholdService {
	@Autowired
	private AnthresholdServiceDao anthresholdServiceDao;

	public List<NoasVLPInventory> getPersonsInfoByLastName(String lastName) {
		return anthresholdServiceDao.findById(lastName);
	}

	public String delete(Anthreshold antThreshold) {
		System.out.println("delete invoked");
		if (!anthresholdServiceDao.exists(antThreshold.getDelete().getNetworkaddress())) {
			anthresholdServiceDao.delete(antThreshold.getDelete().getNetworkaddress());
		}
		return "Opration completed successfully";
	}

	public String saveRequestData(Anthreshold antThreshold) {
		// logger.debug("save operation invoked ...");
		boolean isDataExists = false;

		NoasVLPInventory noasVLPInventory = new NoasVLPInventory();
		noasVLPInventory.setId(antThreshold.getUpsert().getNetworkaddress());
		noasVLPInventory.setAccessNum(antThreshold.getUpsert().getDialedaccessno());
		noasVLPInventory.setBfgCorpId(antThreshold.getUpsert().getBfgcorpid());
		noasVLPInventory.setCreatedtimeStamp(new Date());
		noasVLPInventory.setVlpCorpId("" + antThreshold.getUpsert().getCorpid());
		noasVLPInventory.setEmailList(String.join(",", antThreshold.getUpsert().getEmails().getEmail()));

		Anthreshold.Upsert.Thresholds thresholds = antThreshold.getUpsert().getThresholds();

		StringBuilder sbMoniterType = new StringBuilder();
		StringBuilder sbOperator = new StringBuilder();
		if (thresholds.getCallduration() != null) {
			sbMoniterType.append("Callduration ");
			if (thresholds.getCallduration().getIndividual() != null) {
				sbMoniterType.append("Individual");
				sbOperator.append(thresholds.getCallduration().getIndividual().getOperator());

				noasVLPInventory.setMonitorValue("" + thresholds.getCallduration().getIndividual().getValue());
			} else {
				sbMoniterType.append("Combined");
				sbOperator.append(thresholds.getCallduration().getCombined().getOperator());

				noasVLPInventory.setMonitorValue("" + thresholds.getCallduration().getCombined().getValue());
			}
		} else if (thresholds.getCallcount() != null) {
			if (thresholds.getCallcount().getTotal() != null) {
				sbMoniterType.append("Total Number of Calls");
				sbOperator.append(thresholds.getCallcount().getTotal().getOperator());

				noasVLPInventory.setMonitorWindow(thresholds.getCallcount().getTotal().getWindow());
				noasVLPInventory.setMonitorValue("" + thresholds.getCallcount().getTotal().getValue());
			} else if (thresholds.getCallcount().getAnswered() != null) {
				sbMoniterType.append("Answered Calls");
				sbOperator.append(thresholds.getCallcount().getAnswered().getOperator());

				noasVLPInventory.setMonitorWindow(thresholds.getCallcount().getAnswered().getWindow());
				noasVLPInventory.setMonitorValue("" + thresholds.getCallcount().getAnswered().getValue());
			} else {
				sbMoniterType.append("Unanswered Calls");
				sbOperator.append(thresholds.getCallcount().getUnanswered().getOperator());

				noasVLPInventory.setMonitorWindow(thresholds.getCallcount().getUnanswered().getWindow());
				noasVLPInventory.setMonitorValue("" + thresholds.getCallcount().getUnanswered().getValue());
			}
		}
		noasVLPInventory.setMonitorType(sbMoniterType.toString().trim());
		noasVLPInventory.setMoniterOperator(sbOperator.toString().trim());
		noasVLPInventory.setMonitorValue(noasVLPInventory.getMonitorWindow() == null ? noasVLPInventory

				.getMonitorValue() + "" : noasVLPInventory.getMonitorValue());

		isDataExists = anthresholdServiceDao.exists(noasVLPInventory.getId());
		if (isDataExists) {
			if ((anthresholdServiceDao.findById(noasVLPInventory.getId()).get(0).getLastNotifyTime() != null)) {
				noasVLPInventory.setLastNotifyTime(
						((NoasVLPInventory) anthresholdServiceDao.findById(noasVLPInventory.getId()).get(0))
								.getLastNotifyTime());
			}
		}
		anthresholdServiceDao.save(noasVLPInventory);
		return "Opration completed successfully";
	}

	public String reset(Anthreshold antThreshold) {
		NoasVLPInventory noasVLPInventory = anthresholdServiceDao.findById(antThreshold.getReset().getNetworkaddress())
				.get(0);

		noasVLPInventory.setLastNotifyTime(null);
		anthresholdServiceDao.save(noasVLPInventory);
		return "Opration completed successfully";
	}

	public String delete1(String id) {
		if (id == null)
			throw new RecordNotFoundException(" Networkid should not be null");
		System.out.println("delete invoked");
		if (!anthresholdServiceDao.exists(id)) {
			throw new RecordNotFoundException("NetworkId Not Exists : " + id);
		}
		anthresholdServiceDao.delete(id);
		return "Opration completed successfully";
	}

	public ErrorResponse existsData(String id) {
		if (id == null)
			throw new RecordNotFoundException(" Networkid should not be null");
		return new ErrorResponse("SEARCH is Done", "OK");

	}
}
