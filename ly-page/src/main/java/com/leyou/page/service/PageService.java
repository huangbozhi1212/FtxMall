package com.leyou.page.service;


import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;


@Slf4j
@Service
public class PageService {
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specClient;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long id) {
        Map<String,Object> model = new HashMap<>();
        //查询spu
        Spu spu = goodsClient.querySpuById(id);
        //查询skus
        List<Sku> skus =spu.getSkus();

        //查询详情
        SpuDetail detail = spu.getSpuDetail();
        // String specialSpec = detail.getSpecialSpec();
        //查询brand
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        //  Brand brand = this.brandClient.queryBrandByIds(Collections.singletonList(spu.getBrandId())).get(0);
        // 准备品牌数据
        List<Brand> brands = this.brandClient.queryBrandByIds(
                Arrays.asList(spu.getBrandId()));
        //查询商品分类
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        //查询规格参数
        List<SpecGroup> specs = specClient.queryGroupByCid(spu.getCid3());
        //查询规格参数（附加）


        // 查询规格组及组内参数
        List<SpecGroup> groups = specClient.queryGroupByCid(spu.getCid3());

        // 查询商品分类下的特有规格参数
        List<SpecParam> params =
                this.specClient.querySpecSpecParam(null, spu.getCid3(), null, false);
        // 处理成id:name格式的键值对
        Map<Long,String> paramMap = new HashMap<>();
        for (SpecParam param : params) {
            paramMap.put(param.getId(), param.getName());
        }

        //获取特有规格参数
        model.put("title",spu.getTitle());
        model.put("subTitle",spu.getSubTitle());
        model.put("skus",skus);
        model.put("detail",detail);
        model.put("brand",brand);
        model.put("categories",categories);
        model.put("specs",specs);
        model.put("paramMap", paramMap);
        model.put("groups", groups);
        return model;
    }
    public void createHtml(Long spuId){
        //上下文
        Context context =new Context();
        context.setVariables(loadModel(spuId));
        //输出流
        File dest = new File("D:\\createleyou", spuId + ".html");
        if (dest.exists()){
            dest.delete();
        }
        try (PrintWriter writer = new PrintWriter(dest,"UTF-8")){
            //生成html
            templateEngine.process("item",context,writer);
        }catch (Exception e){
            log.error("【静态页服务异常】",e);
        }
    }

    public void deleteHtml(Long spuId) {
        File dest = new File("D:\\createleyou", spuId + ".html");

        if (dest.exists()){
            dest.delete();
        }
    }
}