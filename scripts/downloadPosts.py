# -*- coding: utf-8 -*-


# encoding=utf8  
import sys  
reload(sys)  
sys.setdefaultencoding('utf8')



from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import base64

import time


# some browser settings
fp = webdriver.FirefoxProfile()
fp.set_preference("browser.download.folderList",2)
fp.set_preference("javascript.enabled", False)

mobileBrowserAgent = "Mozilla/5.0 (BlackBerry; U; BlackBerry 9900; en) AppleWebKit/534.11+ (KHTML, like Gecko) Version/7.1.0.346 Mobile Safari/534.11+"

fp.set_preference("general.useragent.override", mobileBrowserAgent)


driver = webdriver.Firefox(firefox_profile=fp)



driver.get("http://facebook.com")

fbUsername  = "divamgupta"
fbPassword = "!Tgdapass=0"

usernameInputElement = driver.find_element_by_css_selector(".bk.bl.bm")
usernameInputElement.send_keys(fbUsername)



passwordInputElement = driver.find_element_by_css_selector("input[type='password']")
passwordInputElement.send_keys(fbPassword)

driver.find_element_by_css_selector("input[type='submit']").click()




def getPostsOfAFriend(friendName):
	global driver


	textDump = ""
	jsonDump = []

	friendList = [friendName]  # joogar coz im lazy

	years = ["2015" , "2014" , "2013"]

	for friend in friendList:
		driver.get("https://m.facebook.com/" + friend)

		for year in years:

			try:
				driver.find_elements_by_xpath("//*[contains(text(), '"+year+"')]")[-1].click()
			except Exception, e:
				break
			
			

			print "a"

			while True:
				try:
					# driver.find_element_by_css_selector('a:contains("'+"Show More"+'")').click()

					print "b"
					selectionString = ".bp.bo.bq"
					
					# selectionString = '[id^="u_"]'
					posts = driver.find_elements_by_css_selector(selectionString)
					posts = [  x.text for x in posts   ]

					print len(posts)

					if len(posts) == 0:
						selectionString = ".bn.bo.bp"
						posts = driver.find_elements_by_css_selector(selectionString)
						posts = [  x.text for x in posts   ]
						

					for post in posts:
						textDump += "===========================\n"
						textDump += post
						textDump += "\n"

						print "c"

						print post
						print "===========================\n"

						postLines = post.split("\n")

						obj = {}
						try:
							obj['likes'] = int(postLines[-1].split('·')[0])
						except Exception, e:
							obj['likes'] = -1

						print "d"

						if "Like" in postLines[-1]:
							postLines.pop()

						print "e"

						if "· Friends" in postLines[-1]:
							postLines.pop()


						print "f"

						if postLines[-1] == "More":
							postLines.pop()


						print "g"

						obj['text'] = "\n".join(postLines)

						print "h"

						jsonDump.append(obj)

					driver.find_elements_by_xpath("//*[contains(text(), '"+"Show more"+"')]")[-1].click()
					print "i"



					# raw_input()
				except Exception, e:
					print e
					break


	return textDump , jsonDump


def wFile(text , fname):
	import json
	f = open("out/"+fname + ".json" , "w")
	f.write(json.dumps(text[1] ,  indent=4 ))
	f.close()
	f = open("out/"+fname + ".txt" , "w")
	f.write(text[0])
	f.close()
	
		

# for friend in friendList():
friendList = ['sarthak.madan' , 'VJVarunJain' , 'kushagra1996' , 'soumya.sharma13' ,  'lionaneesh' , 'shubham.chauhan1234' , 'jai.sahni.98' , 'bhrigu.gupta.1' , 'shivammax' , 'anam.bhatia' , 'prateekshit.pandey' ]

for friend in friendList:
	xxx = getPostsOfAFriend(friend)
	wFile ( xxx ,  friend)





raw_input()
