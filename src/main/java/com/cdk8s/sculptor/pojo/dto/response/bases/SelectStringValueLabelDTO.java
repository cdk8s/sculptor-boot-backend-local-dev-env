package com.cdk8s.sculptor.pojo.dto.response.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString(callSuper = true)
public class SelectStringValueLabelDTO implements Serializable {

	private static final long serialVersionUID = -1L;

	private String value;
	private String label;

}
