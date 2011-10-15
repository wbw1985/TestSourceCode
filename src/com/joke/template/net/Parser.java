package com.joke.template.net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.joke.template.activity.JokeContentGalleryActivity;
import com.joke.template.logs.SuperLogs;
import com.joke.template.utils.FileUtils;

public class Parser {

	// ----------------------------------------------获得内容

	public static String getContentOfPage(String url) {
		// TODO Auto-generated method stub
		StringBuffer sb = null;
		try {
			URL ur = new URL(url);
			InputStream ss = ur.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ss));
			String s = null;
			sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			ss.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getContent(sb);
	}

	private static String getContent(StringBuffer sb) {
		// TODO Auto-generated method stub
		int start = 0;
		
		if(sb != null){
			int end = sb.indexOf("<body>") + "<body>".length();
			if (start != -1 && end != -1) {
				return sb.substring(start, end);
			}
		}
		return null;
	}

	private static String filter(String content) {
		// 去除连接script
		StringBuffer stringBuffer = new StringBuffer(content);
		String startScriptTag = "<div style=\"float:right;\">";
		String endScriptTag = "</script>--></div>";
		int starScript = stringBuffer.indexOf(startScriptTag);
		int endScript = stringBuffer.indexOf(endScriptTag, starScript);
		if (starScript != -1 && endScript != -1)
			stringBuffer.replace(starScript, endScript + endScriptTag.length(),
					"");

		// 去除连接
		String starthrefTag = "<a href=\"http://www.5time.cn/html";
		String endhrefTag = "(原文链接)</a>";
		int starthref = stringBuffer.indexOf(starthrefTag);
		int endhref = stringBuffer.indexOf(endhrefTag, starthref);
		if (starthref != -1 && endhref != -1)
			stringBuffer.replace(starthref - 1, endhref + endhrefTag.length(),
					"");
		System.out.println(stringBuffer.toString());
		return stringBuffer.toString();
	}

	// 去掉&#8230;
	// 过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		//在过滤之前需要替换掉所有的特殊字符  中文引号：&ldquo;代表左引号。 &rdquo;代表右引号。英文引号&quot;空格&nbsp;
		str = str.replaceAll("&ldquo;", "\"");
		str = str.replaceAll("&rdquo;", "\"");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&nbsp;", " ");
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符? <> / !
		String regEx = "[`~@#$%^&*()+|{}';',\\[\\].?~@#￥%……&*（）——+|{}【】‘；’、]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 获得照片页面中的照片链接
	 * 
	 * @param pageHtml
	 *            照片页面文本
	 * @return 照片链接，若不存在，则返回null
	 */
	
	private static Pattern PICTURE_SRC_LINK = Pattern.compile("src=\"(http:\\/\\/.*?)\"");
	public static void getPictureUrl(String pageHtml) {
		Matcher m = PICTURE_SRC_LINK.matcher(pageHtml);
		if (m.find()) {
			String picUrl = m.group(1);
			if(picUrl != null){
				String imageType = FileUtils.getUrlFileType(picUrl);
				if( imageType.equalsIgnoreCase(".png") || 
					imageType.equalsIgnoreCase(".jpg")|| 
					imageType.equalsIgnoreCase(".jpeg")|| 
					imageType.equalsIgnoreCase(".gif") ||
					imageType.contains("aid") ||
					picUrl.contains("pic.baohe.com")
					){
					
					System.out.println("Type:" + imageType + "    picurl=" + picUrl);
					JokeContentGalleryActivity.picUrltList.add(picUrl);
				}
			}
			int srcStart = pageHtml.indexOf("src");
			if(srcStart != -1)
				getPictureUrl(pageHtml.substring(srcStart + 3));
//			return m.group(1);
		} 
	}
	
	/**
	 * 获取两个url之间的文字信息
	 * @param html
	 */
	public static void getTextFromHtml(String html) {
		int count = JokeContentGalleryActivity.picUrltList.size();
		if(count > 1){
			//获取每一个url之前的text信息
			
			for(int i = 1; i < count; i++){
				String  textString = null;
				if(i == 1){
					String firstUrlString = JokeContentGalleryActivity.picUrltList.get(i);
					int start = 0;
					int end = html.indexOf(firstUrlString) - "<img src=\"".length();
					if(end != -1){
						textString = html.substring(start, end);
					}
				}else{
					String startUrlString = JokeContentGalleryActivity.picUrltList.get(i - 1);
					String endUrlString = JokeContentGalleryActivity.picUrltList.get(i);
					int start = html.indexOf(startUrlString);
					int end = html.indexOf(endUrlString);
					if(start != -1 && end != -1 && start < end){
						textString = html.substring(start, end);
					}
					SuperLogs.info("*******************************************" + startUrlString);
					SuperLogs.info(textString);
					SuperLogs.info("*******************************************" + endUrlString);
				}
				JokeContentGalleryActivity.picBeforeText.add(textString);
			}
		}
		
	}

	/**
	 * 将一些标点符号从html替换成文本
	 * 包含：双引号、省略号、空格以及单引号
	 * @param str
	 * @return
	 */
	public static String quotFilter(String str) {
		str = str.replaceAll("&ldquo;", "\"");
		str = str.replaceAll("&rdquo;", "\"");
		str = str.replaceAll("&quot;", "\"");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&#8230;", "...");
		str = str.replaceAll("&rsquo;", "\'");//&rsquo;
		return str;
	}
	
	private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
	/**
	 * 
	 * 基本功能：过滤所有以"<"开头以">"结尾的标签
	 * <p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String filterHtml(String str) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 基本功能：过滤指定标签
	 * <p>
	 * 
	 * @param str
	 * @param tag
	 *            指定标签
	 * @return String
	 */
	public static String fiterHtmlTag(String str, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		Pattern pattern = Pattern.compile(regxp);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 
	 * 基本功能：替换指定的标签
	 * <p>
	 * 
	 * @param str
	 * @param beforeTag
	 *            要替换的标签
	 * @param tagAttrib
	 *            要替换的标签属性值
	 * @param startTag
	 *            新标签开始标记
	 * @param endTag
	 *            新标签结束标记
	 * @return String
	 * @如：替换img标签的src属性值为[img]属性值[/img]
	 */
	public static String replaceHtmlTag(String str, String beforeTag,
			String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
					.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag
						+ matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 
	 * 基本功能：替换标记以正常显示
	 * <p>
	 * 
	 * @param input
	 * @return String
	 */
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 
	 * 基本功能：判断标记是否存在
	 * <p>
	 * @param input
	 * @return boolean
	 */
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
