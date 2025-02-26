# spring-ai-jug-presentation
JUG Spring AI Presentation

## Code for JUG presentation - "RAG SpringAI"

## Guide

Set the OpenAI API key as an environment variable named OPENAI_API_KEY
```
export OPENAI_API_KEY=your_api_key
```
Run application
```
mvn spring-boot:run
```

The first time you run it, it will take a some time to initialize vector store. Wait for following info
`Vector store loaded` in the logs.

Example requests:
```
# RAG
http :9999/rag question="Describe Spring MCP in three sentences"

# Structured Output
http :9999/beer
http :9999/beer/countries
http :9999/movie
http :9999/movie/list

# Advisor
http :9999/nomemory?msg="My name is Java Duke - how are you?"
http :9999/nomemory?msg="What is my name?"

http :9999/memory?msg="My name is Java Duke - how are you?"
http :9999/memory?msg="What is my name?"

# Function
http :9999/temp
http :9999/temp?city="Torun"

# ETL
http :9999/etl

# Multimodulity
http :9999/imagedescribe
```

 