<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>회 원 가 입</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.0-1/css/all.min.css" />
  <link rel="stylesheet" href="<c:url value='/resources/css/memberJoin.css' />">
  <script src="<c:url value='/resources/js/memberJoin.js' />"></script>
</head>
<body>
  <form:form id="joinFrm" action="${pageContext.request.contextPath }/member/join" 
             modelAttribute="mvo" method="post">
    <div class="mycontainer">
      <div class="content">
        <div><h2 class="join-title">회 원 가 입</h2></div>
        <div><form:label path="id">아이디</form:label></div>
        <div><form:input type="text" path="id" readOnly="true" /><i class="far fa-envelope fa-lg"></i></div>
        <div>
        	<span class="errmsg" id="id_errmsg"></span>
        	<form:errors path="id" cssClass="errmsg"></form:errors>
        </div>
        <div><form:label path="pw">비밀번호</form:label></div>
        <div><form:input type="password" path="pw" /><i class="fas fa-lock fa-lg"></i></div>
        <div>
        	<span class="errmsg" id="pw_errmsg"></span>                
        	<form:errors path="pw" cssClass="errmsg"></form:errors>
        </div>
        <div><label for="pwChk">비밀번호 재확인</label></div>
        <div><input type="password" id="pwChk"><i class="fas fa-lock fa-lg"></i></div>
        <div><span class="errmsg" id="pwChk_errmsg"></span></div>              
        <div><form:label path="tel">전화번호</form:label></div>
        <div><form:input type="tel" path="tel" /><i class="fas fa-mobile-alt fa-lg"></i></div>
        <div>
        	<span class="errmsg" id="tel_errmsg"></span>
					<form:errors path="tel" cssClass="errmsg"></form:errors>
				</div>                   
        <div><form:label path="nickname">별칭</form:label></div>
        <div><form:input type="text" path="nickname" /><i class="fas fa-mobile-alt fa-lg"></i></div>
        <div>
        	<span class="errmsg" id="nickname_errmsg"></span>
					<form:errors path="nickname" cssClass="errmsg"></form:errors>
				</div>                 
        <div><form:label path="region">지역</form:label></div>
        <div>
          <form:select path="region">
            <option value="0">== 선 택 ==</option>
            <form:options path="region" items="${region }" itemValue="code" itemLabel="label" />
          </form:select>
        </div>
				<div>
					<span class="errmsg" id="region_errmsg"></span>                  
					<form:errors path="region" cssClass="errmsg"></form:errors>
				</div>
        <div><form:label path="gender">성별</form:label></div>
        <div>
          <form:radiobuttons path="gender" items="${gender }" itemValue="code" itemLabel="label"/>
        </div>
				<div>
					<span class="errmsg" id="gender_errmsg"></span>                     
					<form:errors path="gender" cssClass="errmsg"></form:errors>
				</div>
        <div><form:label path="birth">생년월일</form:label></div>
        <div><form:input type="date" path="birth" /><i class="fas fa-mobile-alt fa-lg"></i></div>
				<div>
					<span class="errmsg" id="birth_errmsg"></span>           
					<form:errors path="birth" cssClass="errmsg"></form:errors>
				</div>
				<div style="color:#f00;font-weight:bold;font-size:0.8.rem">${svr_msg }</div>                    
        <div><button type="submit" id="joinBtn">가 입 하 기</button></div>
      </div>
    </div>
  </form:form>
</body>
</html>