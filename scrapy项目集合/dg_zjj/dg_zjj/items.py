# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class DgZjjItem(scrapy.Item):

    id = scrapy.Field()
    # 档案编号 有些公司没有这个字段
    file_number = scrapy.Field()

    #企业名字
    company_name = scrapy.Field()

    #企业详细地址
    company_address = scrapy.Field()

    #信用分 与总分值互斥，有信用无总分，二选一

    credit_score = scrapy.Field()
    #总分值 与总分值互斥，有信用无总分，二选一
    #total_score = scrapy.Field()

    #办公地址 有些公司没有这个字段
    business_address = scrapy.Field()

    #企业类型
    company_type = scrapy.Field()

    #联系电话
    phone_number = scrapy.Field()

    #电子邮件
    email = scrapy.Field()

    #状态-登记or未
    status = scrapy.Field()

    #法人代表
    legal_person =scrapy.Field()

    #审批时间
    approve_time =scrapy.Field()

    #传真
    fax = scrapy.Field()

    #安全生产许可证——只有建筑类有
    safety_licence = scrapy.Field()

    # 安全生产许可证有效期——只有建筑类有
    safety_indata  = scrapy.Field()

    #资质——需要判断是否有多个资质写一个循环爬取多个资质
    licence = scrapy.Field()

    #等级
    level = scrapy.Field()
    #有效期
    indate = scrapy.Field()
# #主管部门
    # manage_depart = scrapy.Field()