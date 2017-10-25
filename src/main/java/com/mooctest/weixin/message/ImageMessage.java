package com.mooctest.weixin.message;

/**
 * 图片消息
 * 
 * @author Aaron
 * @date 2014-6-23
 */
public class ImageMessage extends BaseMessage {
//	// 图片链接
//	private String PicUrl;

	private Image Image;

//	public String getPicUrl() {
//		return PicUrl;
//	}
//
//	public void setPicUrl(String picUrl) {
//		PicUrl = picUrl;
//	}

	public Image getImage() {
		return Image;
	}

	public void setImage(Image Image) {
		this.Image = Image;
	}
}