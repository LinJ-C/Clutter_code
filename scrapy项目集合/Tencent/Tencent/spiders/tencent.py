# -*- coding: utf-8 -*-
import scrapy
import json
from Tencent.items import TencentItem

class TencentSpider(scrapy.Spider):
    name = 'tencent'
    allowed_domains = ['tencent.com']
    base_url = 'https://careers.tencent.com/tencentcareer/api/post/Query?timestamp=1592533003431&countryId=&cityId=&bgIds=&productId=&categoryId=&parentCategoryId=&attrId=&keyword=&pageIndex=1&pageSize=5000&language=zh-cn&area=cn'
    start_urls = [base_url]

    def parse(self, response):

        Data_all = json.loads(response.body_as_unicode())
        node_list = Data_all['Data']['Posts']
        for node in node_list:
            item = TencentItem()
            item['RecruitPostName'] = node['RecruitPostName']
            if len(node['CountryName']):
                item['CountryName'] = node['CountryName']
            else:
                item['CountryName'] = "中国"
            item['LocationName'] = node['LocationName']
            item['CategoryName'] = node['CategoryName']
            item['Responsibility'] = node['Responsibility']
            item['LastUpdateTime'] = node['LastUpdateTime']
            item['PostURL'] = node['PostURL']
            yield item

        # if (self.add_url<585):
        #     self.add_url += 1
        #     url = self.base_url+str(self.add_url)
        #     yield scrapy.Request(url,callback=self.parse)

