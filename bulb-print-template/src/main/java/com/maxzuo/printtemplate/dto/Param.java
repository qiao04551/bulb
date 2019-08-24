package com.maxzuo.printtemplate.dto;

import java.io.Serializable;

/**
 * 统一接收参数类型
 */
public class Param implements Serializable {

	private static final long serialVersionUID = 7222428979812453935L;

	/** 接口的方法名 */
	private String cmd;

	/** 版本号 */
	private String version;

	/** 参数 */
	private Object data;

	public Param() {
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Param{" +
				"cmd='" + cmd + '\'' +
				", version='" + version + '\'' +
				", data=" + data +
				'}';
	}
}
