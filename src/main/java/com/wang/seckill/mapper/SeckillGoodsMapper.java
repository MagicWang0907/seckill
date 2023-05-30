package com.wang.seckill.mapper;

import com.wang.seckill.entity.SeckillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2022-02-19
 */

public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods> {
    @Select("SELECT\n" +
            "\tg.id,\n" +
            "\tg.goods_name,\n" +
            "\tg.goods_title,\n" +
            "\tg.goods_detail,\n" +
            "\tg.goods_img,\n" +
            "\tg.goods_stock,\n" +
            "\tg.goods_price,\n" +
            "\tsg.seckill_price,\n" +
            "\tsg.stock_count,\n" +
            "\tsg.start_date,\n" +
            "\tsg.end_date\n" +
            "from\n" +
            "\tt_goods g\n" +
            "LEFT JOIN t_seckill_goods sg\n" +
            "ON g.id=sg.goods_id;")
    List<GoodsVO> selectSeckillGoodsDetail();

    @Select("SELECT\n" +
            "\tg.id,\n" +
            "\tg.goods_name,\n" +
            "\tg.goods_title,\n" +
            "\tg.goods_detail,\n" +
            "\tg.goods_img,\n" +
            "\tg.goods_stock,\n" +
            "\tg.goods_price,\n" +
            "\tsg.seckill_price,\n" +
            "\tsg.stock_count,\n" +
            "\tsg.start_date,\n" +
            "\tsg.end_date\n" +
            "from\n" +
            "\tt_goods g\n" +
            "LEFT JOIN t_seckill_goods sg\n" +
            "ON g.id=sg.goods_id" +
            " where g.id = #{param1};")
    GoodsVO selectOneGoodsById(Long goodsID);
}
