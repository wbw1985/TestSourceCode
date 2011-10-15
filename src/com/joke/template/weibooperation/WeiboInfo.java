package com.joke.template.weibooperation;

import java.util.List;




//书籍公用信息
public class WeiboInfo
{
	public WeiboInfo(long nID)
	{
		m_id = nID;
	}
	
	
	public long	m_id = 0;
	public long m_authod_id = 0;
	public String m_sauthor = "";			//作者
	public String m_sAuthorImgUrl = "";		//作者头像URL
	public String m_sContent = "";			//微博内容
	public String m_sContentPicUrl = "";	//微博图片
	public String m_sContentPicUrl_big = "";	//微博大图片
	
	public long   m_sub_id = 0;					//子微博作者ID
	public String m_sSubAuthor = "";			//子微博作者
	public String m_sSubContent = "";			//子微博内容
	public String m_sSubContentPicUrl = "";		//子微博图片
	public String m_sSubContentPicUrl_big = "";	//子微博大图片
	public List<String> m_tag_list;

	public String m_sTime = "";				//发表时间
	public String m_source = "";
	public boolean m_is_vip = false;
	

	
};