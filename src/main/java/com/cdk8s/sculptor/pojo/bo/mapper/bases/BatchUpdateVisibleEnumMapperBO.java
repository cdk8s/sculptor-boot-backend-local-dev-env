package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BatchUpdateVisibleEnumMapperBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private List<Long> idList;
	private Integer visibleEnum;
	private Long updateDate;
	private Long updateUserId;
}
