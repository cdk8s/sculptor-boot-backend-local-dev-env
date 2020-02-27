package com.cdk8s.sculptor.pojo.bo.service.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class BatchUpdateVisibleEnumServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private List<Long> idList;
	private Integer visibleEnum;
	private Long updateDate;
	private Long updateUserId;

}
