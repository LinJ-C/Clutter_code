# -*- coding: utf-8 -*-
from copy import deepcopy

import scrapy
import json
from dg_zjj.items import DgZjjItem
from bs4 import BeautifulSoup
import sys
sys.setrecursionlimit(100000)

class DgConstructionSpider(scrapy.Spider):
    name = 'dg_construction'
    allowed_domains = ['http://zjj.dg.gov.cn']
    ###对应关系
    # 1 - 工程勘察
    # 2 - 工程设计
    # 3 - 工程监理
    # 4 - 工程质量
    # 5 - 招标代理
    # 6 - 工程造价
    # 7 - 施工图审
    # 10 - 房屋安全鉴定
    # 11 - 建筑施工
    # 12 - 信用担保
    # 13 - 室内环境

    start_urls = ['http://zjj.dg.gov.cn/mware_cms/query/entInfoList.action?entType=12&&rows=10']

    # 第二页的url
    next_url = 'http://zjj.dg.gov.cn/mware_cms/query/viewEntInfo.action?id='
    # 第二页的url
    last_url = 'http://zjj.dg.gov.cn/mware_cms/query/viewOtherEntInfo.action?id='
    # 公司id通过第一页获取
    #id = 0

    def parse(self, response):
        Data_all = json.loads(response.body_as_unicode())
        node_all = Data_all['rows']
        item = DgZjjItem()
        for node in node_all:
            item['file_number'] = node['creditNo']
            item['company_name'] = node['entName']
            item["credit_score"] = node['regMoney']
            item['company_type'] = node['entType']
            item['legal_person'] = node['extend1']
            item['company_address'] = node['entAddress']
            item['approve_time'] = node['approveDate']
            #少数类型有这个字段 4 7 10 12 13
            item['business_address'] = node['setOfficeaddress']

            canBid = node['canBid']
            isView = node['isView']

            item['id'] = node['id']

            if (canBid == '1' and isView == None):
                item['status'] = node['canBid']
            elif(canBid == None and isView == '1'):
                item['status'] = '2'
            elif (canBid == None and isView == '3'):
                item['status'] = '3'
            else:
                item['status'] = None

            url1 = self.next_url+item['id']
            #print(url1)
            yield scrapy.http.Request(url1,  meta={'item': deepcopy(item)}, dont_filter=True, callback=self.next_page)
            print("返回到第一个页面")

    ##type 4，5的结构与其他有差别需要单独写一个第二页的规则
    def next_page(self, response):
        item = response.meta['item']
        print('来到第二个页面')
        print(item)
        # 将response获取的乱码转化为str，然后转化为BeautifulSoup能识别是格式
        html = BeautifulSoup(response.body.decode(response.encoding))
        td_list = html.find_all('td')
        # print(td_list)


        # #适用于 1 2 3 6 11
        # item['phone_number'] = td_list[15].string
        # item['fax'] = td_list[17].string
        # item['email'] = td_list[19].string
        # #这两个字段只有11-建筑施工有
        # item['safety_licence'] = td_list[31].string
        # item['safety_indata'] = td_list[33].string

        #适用于4，5，7,10，13  12
        item['phone_number'] = td_list[17].string
        item['fax'] = td_list[21].string
        item['email'] = td_list[23].string

        url2 = self.last_url+item['id']

        yield scrapy.http.Request(url2,  meta={'item': deepcopy(item)}, callback=self.last_page, dont_filter=True)

    def last_page(self, response):
        print('来到第三个页面')
        html = BeautifulSoup(response.body.decode(response.encoding))
        table = html.find('table', class_='u-table')
        tr_list = table.find_all('tr')[1:]
        item = response.meta['item']
        for tr in tr_list:
            item['licence'] = tr.td.string
            item['level'] = tr.td.next_sibling.next_sibling.string
            item['indate'] = tr.td.next_sibling.next_sibling.next_sibling.next_sibling.next_sibling.next_sibling.string
            yield item
            # yield self.item

