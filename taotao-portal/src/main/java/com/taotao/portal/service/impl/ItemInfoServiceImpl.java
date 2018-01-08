package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemInfoService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class ItemInfoServiceImpl implements ItemInfoService {

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;

	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	@Override
	public ItemInfo getIntemInfo(long itemId) {
		try {
			// 调用taotao-rest服务
			String resultStr = HttpClientUtil.doGet(ITEM_INFO_URL + itemId);
			if (!StringUtils.isEmpty(resultStr)) {
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbItem.class);
				TbItem item = (TbItem) result.getData();
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setId(item.getId());
				itemInfo.setTitle(item.getTitle());
				itemInfo.setSellPoint(item.getSellPoint());
				itemInfo.setPrice(item.getPrice());
				itemInfo.setImage(item.getImage());
				return itemInfo;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TbItemDesc getItemDesc(long itemId) {
		// 调用taotao-rest服务
		try {
			String resultStr = HttpClientUtil.doGet(ITEM_DESC_URL + itemId);
			if (!StringUtils.isEmpty(resultStr)) {
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbItemDesc.class);
				TbItemDesc itemDesc = (TbItemDesc) result.getData();
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemParam(long itemId) {
		try {
			// 调用taotao-rest服务
			String resultStr = HttpClientUtil.doGet(ITEM_PARAM_URL + itemId);
			if (!StringUtils.isEmpty(resultStr)) {
				TaotaoResult result = TaotaoResult.formatToPojo(resultStr, TbItemParamItem.class);
				TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
				String paramData = itemParamItem.getParamData();

				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);

				StringBuffer sb = new StringBuffer();

				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for (Map m1 : jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for (Map m2 : list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
						sb.append("            <td>" + m2.get("v") + "</td>\n");
						sb.append("        </tr>\n");
					}
				}

				sb.append("    </tbody>\n");
				sb.append("</table>");
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
