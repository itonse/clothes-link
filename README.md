# ClothesLink
### 이커머스 프로젝트
---
## 프로젝트 기능 및 설계

### 상품
- 상품 카테고리 적용
  - 카테고리 등록, 수정
- 상품 보기
  - 카테고리별로 리뷰 많은 순, 최신 등록순 (5개씩 페이징 처리)
  - 상품 리뷰 보기 (최신순으로 5개씩 보기)
- 상품 검색
  - 상품명으로 검색
  - 일치 상품 없을 시 해당 키워드가 들어간 상품 최대 5개까지 출력
- 상품 등록
  - 등록, 수정, 삭제 구현
  - 등록 시 작성 항목: 카테고리 번호, 상품명, 상품설명, 재고, 가격

### 판매자
- 회원가입 
  - 이메일로 본인인증
- 로그인 
  - JWT토큰 발급 (유효기간 1일)

### 고객
- 회원가입 
  - 이메일로 본인인증 
- 로그인 
  - JWT토큰 발급 (유효시간 2시간)
- 장바구니 기능
  - 로그인 필요
  - 상품 등록
  - 상품 수량 수정
  - 상품 삭제
  - 상품 등록/수정 시 재고 보다 같거나 적은 수량만 담을 수 있다.
- 상품 주문
  - 로그인 필요
  - 결제 양식 작성
  - 재고 보다 적은 수량만 구입 가능 하다.
  - 주문 전에 상품의 재고 수량을 확인하여 장바구니에 담은 수량보다 적은 상품이 있을 시, 주문 불가
- 결제 내역
  - 주문 건에 대해 구매확정을 할 수 있다. 
  - 구매확정을 하게 되면 해당 주문 건의 모든 상품의 리뷰를 작성할 수 있다. (주문건은 주문번호로 식별)
  - 히스토리는 최신순으로 5개씩 페이징 처리

### 리뷰
- 리뷰 작성
  - 구매자 로그인 필요
  - 상품 구매확정 시 작성 가능
  - 주문 건당 1개의 리뷰만 작성 가능
  - 삭제된 상품이면 리뷰 작성 불가
- 리뷰 답글
  - 판매자 로그인 필요
  - 리뷰당 1개의 답글만 작성 가능

</br>

## ERD 
![ClothesLink](https://github.com/itonse/ClothesLink/assets/76129297/d61acb3a-970e-42d2-9dfb-f5eed785831a)

</br>

## Trouble Shooting
[go to the trouble shooting section](doc/TROUBLE_SHOOTING.md)

### Tech Stack
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=git&logoColor=white">
</div>
