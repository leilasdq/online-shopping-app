# online shopping app
This is a simple app for an online shoping.

# Screenshots
<p float="left">
    <img src="https://user-images.githubusercontent.com/23487067/74089589-fa7a2180-4ab7-11ea-9ca6-f96b7c100fa0.jpg" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/74089604-2d241a00-4ab8-11ea-8d96-b666f5512066.jpg" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/74089696-0dd9bc80-4ab9-11ea-8097-ddb5254b2c2c.jpg" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919465-c636ff00-3d5c-11ea-8286-0066d71e8b57.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919540-e5359100-3d5c-11ea-9fe5-319e65e9a255.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919600-01393280-3d5d-11ea-92be-fe8bb236c907.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72923153-b7077f80-3d63-11ea-8fb1-a7cfdb40860a.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72923249-eae2a500-3d63-11ea-810e-4d8bd92af979.png" width="140" height="250" />    
    <img src="https://user-images.githubusercontent.com/23487067/74090694-94939700-4ac3-11ea-8f6a-daead808b5f7.jpg" width="140" height="250" />  
    <img src="https://user-images.githubusercontent.com/23487067/74090719-e6d4b800-4ac3-11ea-9c11-82dcfcb980b0.jpg" width="140" height="250" /> 
    <img src="https://user-images.githubusercontent.com/23487067/74090729-066be080-4ac4-11ea-9e0c-d266d9c3813a.jpg" width="140" height="250" /> 
    <img src="https://user-images.githubusercontent.com/23487067/74090746-31563480-4ac4-11ea-88ba-9d1682df0acd.jpg" width="140" height="250" /> 
    <img src="https://user-images.githubusercontent.com/23487067/74090755-61053c80-4ac4-11ea-918d-e614e42e3593.jpg" width="140" height="250" /> 
    <img src="https://user-images.githubusercontent.com/23487067/74090769-88f4a000-4ac4-11ea-9ff7-23051d967b7b.jpg" width="140" height="250" /> 
</p>

# Description
- This app is a sample online shoping program.
- Application language is Persian.
- Products are on a Woocomerce website. I'm using Retrofit library to fetch items from net.
- It works when internet is connected. If not, app will alert you with a picture. You can go back to previous page after mobile net (or wifi) is reconnected by pressing button.
- At first you will see a splash screen for some seconds (At this page a Lottie animation is showing) and after that main page will be apear.
- At this page you will see 3 types of products: *New Products*, *Most rated ones* and *Popular products*. Each part contains 10 products.
- For see all products of that part there is a clicable TextView at left side. By pressing this part, app will shows another page wich has endless recycler view, so you can see all products of that part.
- By clicking every single product there will be a new page for its details; all pictures, name, detail of product, real and sale price.
- There is another part in first page: categories. By clickig this, a new page will apear that shows a viewPager of categories with its sub categories and if you click each sub category a list of its products will appear.
- There is a Buying card to save what user want to complete to pay and buy for it. At this part GreenDao database had been used.If there is nothing on db a picture will show to alert user.
- Also there is a badge in toolbar icon to show how many items are there in user's card.
- There is a place for users to login or sign up to the app.
- User can search for items in toolbar.If there is no result a picture will be shown to alert.
