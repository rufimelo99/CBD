from pymongo import *
from datetime import datetime
client = MongoClient()
db = client.cbd
collection = db.rest

def insert_doc(json_doc):
    try:
        inserted_id = collection.insert_one(json_doc).inserted_id
        print("JSON FILE inserted")

    except Exception as e:
        print("Error: ", e)

def change_entry(condition, alteration):
    try:
        x = collection.update_many(condition, alteration)
    except Exception as e:
        print("Error: ", e)

def query_to_search(query):
    for post in collection.find(query):
        print(post)

#def add_index(index, condition):
#    result=collection.create_index(index, condition)

#add_index([('user_id', pymongo.ASCENDING)], unique=True) #does not work

def countLocalidades():
    localidades = collection.aggregate([{"$group": {"_id": "$localidade"}}])
    print("Numero de localidades distintas: {}".format(len(list(localidades))))

def countRestByLocalidade():
    print("Numero de restaurantes por localidade:")
    localidades = collection.aggregate([{"$group": {"_id": "$localidade", "Counter": {"$sum": 1}}}])
    for i in localidades:
        print("-> {}: {}".format(i['_id'], i['Counter']))

def countRestByLocalidadeByGastronomia():
    print("Numero de restaurantes por localidade e gastronomia:")
    localidades = collection.aggregate([{"$group": {"_id": {"localidade":"$localidade", "gastronomia":"$gastronomia"}, "Counter": {"$sum": 1}}}])
    for i in localidades:
        print("-> {} | {} : {}".format(i["_id"]["localidade"], i["_id"]["gastronomia"], i["Counter"]))


def getRestWithNameCloserTo(name):
    print("Nome de restaurantes contendo '{}' no nome:".format(name))
    #https://docs.mongodb.com/manual/reference/operator/aggregation/match/
    restaurantes = collection.aggregate([{"$match": {"nome": {"$regex": name}}}])
    for i in restaurantes:
        print("-> {}".format(i["nome"]))





insert_doc({"address": {"building": "123", "coord": [-1.0, -2.0], "rua": "rua", "zipcode": "456"}, "localidade": "Aveiro", "gastronomia": "None", "grades": [{"date": datetime.utcnow(), "grade": "F", "score": 7}], "nome": "WELP", "restaurant_id": "0101010101"})
query_to_search({"gastronomia": "None"})
change_entry({"gastronomia": "None"},{"$set": {"zipcode": "000"}})
query_to_search({"gastronomia": "None"})
countLocalidades()
countRestByLocalidade()
countRestByLocalidadeByGastronomia()
getRestWithNameCloserTo("WE")
