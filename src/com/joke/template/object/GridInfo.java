package com.joke.template.object;
/**
 * Copyright (C) 2010,Under the supervision of China Telecom Corporation
 * Limited Guangdong Research Institute
 * The New Vphone Project
 * @Author fonter.yang
 * @Create date£º2010-10-11
 * 
 */
public class GridInfo {

	private String name;
	private int ImageId;

	
	public GridInfo(String name, int drawableId) {
		super();
		this.name = name;
		this.ImageId = drawableId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getImageId() {
		return ImageId;
	}

	public void setImageId(int ImageId) {
		this.ImageId = ImageId;
	}
	
}
