package com.mrathena.message.kafka.producer;

import com.bestpay.marketing.api.common.model.response.RebateCoreResultDTO;
import com.bestpay.marketing.api.common.model.response.RebateMsgDTO;
import com.google.gson.Gson;
import com.mrathena.common.toolkit.IdKit;
import com.mrathena.common.toolkit.LogKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mrathena on 2019/11/6 22:05
 */
@Slf4j
@Component
public class KafkaTest {

	@Resource(name = "hxKafkaTemplate")
	private KafkaTemplate<String, String> hxKafkaTemplate;

	@Scheduled(cron = "0/10 * * * * ?")
	public void kafkaTest() {
		LogKit.setTraceNo(IdKit.getUuid());
		RebateMsgDTO message = new RebateMsgDTO();
		message.setHandlingStatus("SUCCESS");
		message.setResponseCode("Code");
		message.setResponseContent("Message");
		RebateCoreResultDTO result = new RebateCoreResultDTO();
		result.setRequestNo(IdKit.getSerialNo());
		message.setRebateCoreResultDTO(result);
		hxKafkaTemplate.send("marketing_project_rebate_result", new Gson().toJson(message));
	}

}
