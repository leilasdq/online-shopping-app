# online shopping app
This is a simple app for an online shoping.

# Screenshots
<p float="left">
    <img src="https://user-images.githubusercontent.com/23487067/72919249-60e30e00-3d5c-11ea-98a7-2102728d3c78.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919304-7b1cec00-3d5c-11ea-827c-62fe478e9fd7.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919383-9e479b80-3d5c-11ea-92fe-792d28d95946.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919465-c636ff00-3d5c-11ea-8286-0066d71e8b57.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919540-e5359100-3d5c-11ea-9fe5-319e65e9a255.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72919600-01393280-3d5d-11ea-92be-fe8bb236c907.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72923153-b7077f80-3d63-11ea-8fb1-a7cfdb40860a.png" width="140" height="250" />
    <img src="https://user-images.githubusercontent.com/23487067/72923249-eae2a500-3d63-11ea-810e-4d8bd92af979.png" width="140" height="250" />    
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
