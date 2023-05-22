package com.wang.seckill.service;

import com.wang.seckill.entity.SeckillGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.seckill.entity.User;
import com.wang.seckill.vo.GoodsVO;
import org.springframework.ui.Model;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2022-02-19
 */
public interface ISeckillGoodsService extends IService<SeckillGoods> {


    List<GoodsVO> findGoods();

    GoodsVO findGoodsVoByID(Long goodsID);

    String doSecKill(Model model, User user,Long goodsId);
}
