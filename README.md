# Resume project for clevertec
Sping boot REST-Service for working with resumes, crud operations

# About the Service
The service provides a Rest-service for working with resumes.
The service has two profiles 'test' and 'prod'.
'test' profile uses H2 database. 'prod' profile uses PostgreSQL database.
The service saves files in Json format, and outputs files in binary form

# Interaction with the service

## Save resume
```
POST http://localhost:8080/api/v1/resumes
Content-Type: application/json
{
   "firstName": "Alisa",
    "secondName": "Egorova",
    "phoneNumber": "+375-29-3314155",
    "email": "AlisaEg@gmail.com",
    "dateOfBirth": "2000-01-21"
}
RESPONSE: HTTP 201 (Created)
Content-Type: application/x-protobuf
```

## Update resume
```
PUT http://localhost:8080/api/v1/resumes/{id}
Content-Type: application/json
{
   "firstName": "Alisa",
    "secondName": "Egorova",
    "phoneNumber": "+375-29-3314155",
    "email": "AlisaEg@gmail.com",
    "dateOfBirth": "2000-01-21"
}
RESPONSE: HTTP 200 (OK)
Content-Type: application/x-protobuf
```

## Get resume by id
```
GET http://localhost:8080/api/v1/resumes/{id}
Content-Type: application/json

RESPONSE: HTTP 200 (OK)
Content-Type: application/x-protobuf
```
## Get all resumes 

```
GET http://localhost:8080/api/v1/resumes
Content-Type: application/json

RESPONSE: HTTP 200 (OK)
Content-Type: application/x-protobuf
```

## Get all resumes by filter
Filter options: firstName, secondName, phoneNumber, email, dateOfBirth
```
GET http://localhost:8080/api/v1/resumes?filter=firstName:Alisa
Content-Type: application/json

RESPONSE: HTTP 200 (OK)
Content-Type: application/x-protobuf
```

## Get all resumes with pagination
Pagination options: page, size
```
GET http://localhost:8080/api/v1/resumes?pageable=page:1,size:4
Content-Type: application/json

RESPONSE: HTTP 200 (OK)
Content-Type: application/x-protobuf
```

#
