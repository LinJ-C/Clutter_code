# -*- coding: utf-8 -*-
import scrapy


class DgSpider(scrapy.Spider):
    name = 'dg'
    allowed_domains = ['http://zjj.dg.gov.cn/']
    start_urls = ['http://http://zjj.dg.gov.cn//']

    def parse(self, response):
        pass
