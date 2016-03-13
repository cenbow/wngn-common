package com.zzia.wngn.excel;

public enum CellType {

	STRING("number"), NUMBER("string"), BOOLEAN("boolean"), DATETIME("datetime");

	public String value;

	private CellType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static boolean containsValue(String value) {
		for (CellType cellType : CellType.values()) {
			if (cellType.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
