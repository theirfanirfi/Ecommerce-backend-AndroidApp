import pymysql
import json
import os, sys
#open database connection

# db = pymysql.connect("127.0.0.1","root","root","barber")

# #prepare cursor

# cursor = db.cursor()

# #execute sql query

# cursor.execute("select * from appointments")

# data = cursor.fetchall()
# j = json.dumps(str(data))
# print(json.loads(j))

#db.close()

class MyDb:
	def executeSelectCommand(table,wildcard):
		os.system("clear")
		os.system("mysql -u root --password=root -h 127.0.0.1 -D barber -e 'select "+wildcard+" from "+table+"'")

# os.system("clear")

# os.system(" mysql -u root --password=root -h 127.0.0.1 -D barber -e 'select * from users'")

if __name__ == "__main__":
	db = MyDb
	if len(sys.argv) > 1 and len(sys.argv) < 3:
		table = sys.argv[1]
		db.executeSelectCommand(table,"*")
	elif len(sys.argv) > 2:
		table = sys.argv[1]
		wildcard = sys.argv[2]
		db.executeSelectCommand(table,wildcard)
	else:
		print("table or wildcard arguments must be provided.")
	#db.executeSelectCommand("users","id")

