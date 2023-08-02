<!DOCTYPE html>
<html>
<head>
    PuneetVerse - Short Video Rest API
</head>
<body>
  <h1>PuneetVerse</h1>
  <p>Welcome to PuneetVerse, a short video REST API for video memes and templates downloader made with Spring Boot.</p>
  
  <h2>Technologies Used</h2>
  <div style="display: flex; align-items: center;">
    <img src="https://brandslogos.com/wp-content/uploads/images/large/java-logo-1.png" alt="Java Logo" height="90px" style="margin-right: 10px;">
    <img src="https://keyholesoftware.com/wp-content/uploads/Spring-Boot.png" alt="Spring Boot Logo" height="80px" style="margin-right: 10px;">
    <img src="https://secureservercdn.net/160.153.137.14/fad.0cb.myftpupload.com/wp-content/uploads/2021/03/MongoDB-768x462.jpg" alt="MongoDB Logo" height="80px" style="margin-right: 10px;">
    <img src="https://www.techinjections.com/wp-content/uploads/2020/01/hibernate.jpg" alt="Hibernate Logo" height="80px" style="margin-right: 10px;">
    <img src="https://miro.medium.com/v2/resize:fit:640/1*B9CIOrxdROHvtdmouQA1_A.png" alt="Amazon S3 Logo" height="80px" style="margin-right: 10px;">
    <img src="https://www.driftingruby.com/episodes/deploying-to-aws-elastic-beanstalk/download_image.png" alt="AWS Elastic Beanstalk Logo" height="80px" style="margin-right: 10px;">
    <img src="https://www.talentica.com/wp-content/uploads/2019/08/junit5-1.png" height="80px" style="margin-right: 10px;">
    <img src="https://logowik.com/content/uploads/images/swagger6360.jpg" alt="Swagger Logo" height="80px">
  </div>
  
  <h2>Endpoints</h2>
  <p>Here are the main endpoints for PuneetVerse API:</p>
  <ul>
    <li>Random Video Memes: <code>GET http://puneetverse.eu-north-1.elasticbeanstalk.com/puneetverse/memes?pageNumber=0&pageSize=10</code></li>
    <li>Latest Videos: <code>GET http://puneetverse.eu-north-1.elasticbeanstalk.com/puneetverse/latestmemes?pageNumber=0&pageSize=10</code></li>
    <li>Trending Videos: <code>GET http://puneetverse.eu-north-1.elasticbeanstalk.com/puneetverse/trendingmemes?pageNumber=0&pageSize=10</code></li>
    <li>Search Videos: <code>GET http://puneetverse.eu-north-1.elasticbeanstalk.com/puneetverse/search?caption=puneet&pageNumber=0&pageSize=10</code></li>
  </ul>
  
  <h2>API Documentation</h2>
  <p>Explore the API documentation using Swagger UI:</p>
  <p><a href="http://puneetverse.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html" target="_blank">PuneetVerse Swagger Documentation</a></p>
</body>
</html>
