# lucene-demo
lucene 学习, 构建索引以及搜索过程

## 数据库

数据库文件 tb_sku.sql

~~~mysql
/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : lucene

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-11-12 10:24:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_sku
-- ----------------------------
DROP TABLE IF EXISTS `tb_sku`;
CREATE TABLE `tb_sku` (
  `id` varchar(20) NOT NULL COMMENT '商品id',
  `name` varchar(200) NOT NULL COMMENT 'SKU名称',
  `price` int(20) NOT NULL COMMENT '价格（分）',
  `num` int(10) NOT NULL COMMENT '库存数量',
  `image` varchar(200) DEFAULT NULL COMMENT '商品图片',
  `category_name` varchar(200) DEFAULT NULL COMMENT '类目名称',
  `brand_name` varchar(100) DEFAULT NULL COMMENT '品牌名称',
  `spec` varchar(200) DEFAULT NULL COMMENT '规格',
  `sale_num` int(11) DEFAULT '0' COMMENT '销量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of tb_sku
-- ----------------------------
INSERT INTO `tb_sku` VALUES ('100000003145', 'vivo X23 8GB+128GB 幻夜蓝 水滴屏全面屏 游戏手机 移动联通电信全网通4G手机', '95900', '10000', 'https://m.360buyimg.com/mobilecms/s720x720_jfs/t1/4612/28/6223/298257/5ba22d66Ef665222f/d97ed0b25cbe8c6e.jpg!q70.jpg.webp', '手机', 'vivo', '{\'颜色\': \'红色\', \'版本\': \'8GB+128GB\'}', '0');
INSERT INTO `tb_sku` VALUES ('100000004580', '薇妮(viney)女士单肩包 时尚牛皮女包百搭斜挎包女士手提大包(经典黑)', '87900', '10000', 'https://m.360buyimg.com/mobilecms/s720x720_jfs/t5590/64/5811657380/234462/5398e856/5965e173N34179777.jpg!q70.jpg.webp', '真皮包', 'viney', '{\'颜色\': \'黑色\'}', '0');
............

~~~

 下载链接(8w+数据)：
https://pan.baidu.com/s/1cuoi6pPAOlUX0B4M6h2nAg

提取码： sqhm



## 参考
1) 视频讲解： https://www.bilibili.com/video/BV1Na411h7kk?p=10&spm_id_from=pageDriver
2) gitee源码： https://gitee.com/kenxiaoyue/Lucene
