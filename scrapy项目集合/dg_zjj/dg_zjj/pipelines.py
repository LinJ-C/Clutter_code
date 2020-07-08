# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html
import json
import pymysql


class DgZjjPipeline(object):

    def open_spider(self, spider):
        # 写入磁盘的操作
        # self.f = open("dgzjj.csv", "w")

        db = 'data'
        host = '192.168.0.187'
        port = 3306
        user = 'data'
        passwd = 'data@123456'

        self.db_conn = pymysql.connect(host=host, port=port, db=db, user=user, passwd=passwd, charset='utf8')

        self.db_cur = self.db_conn.cursor()

    def process_item(self, item, spider):
        # 写入磁盘的操作
        # content = json.dumps(dict(item), ensure_ascii=False)+',\n'
        # self.f.write(content)
        # #, encode='utf-8'
        self.insert_db(item)
        return item

    def close_spider(self, spider):
        # self.f.close()
        self.db_conn.commit()
        self.db_conn.close()

    def insert_db(self, item):
        values = (
            item['file_number'],
            item['company_name'],
            item['credit_score'],
            item['company_type'],
            item['legal_person'],
            item['company_address'],
            #4 5,7，10 12 13有这个字段
            item['business_address'],
            item['approve_time'],
            item['status'],
            item['phone_number'],
            item['fax'],
            item['email'],
            # #只有11有
            # item['safety_licence'],
            # item['safety_indata'],

            item['licence'],
            item['level'],
            item['indate'],
        )
        #safety_licence, safety_indata,这两个只有type 11有、、因此少两个字段,%s,%s 、、4 13有这个字段 business_address, ,%s
        sql = "INSERT INTO info_building_company(file_number, company_name, credit_score, company_type, legal_person, company_address, business_address, approve_time, status, " \
              "phone_number, fax, email, licence, level, indate) VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        #sql = "UPDATE info_building_company SET file_number=item['file_number'] "
        self.db_cur.execute(sql, values)




