# HIndicator RecyclerView横向滑动指示器

现在很多app除了banner以外，集中的功能展示区都会有这种需要展示多个，甚至翻页的情况，通常的设计有两种，
一种是类似于banner的翻页的，比如京东app；另一种是平滑的滚动，比如淘宝app。

下面我我们实际看一下这个效果

淘宝首页

![taobao](https://github.com/leiyun1993/HIndicator/raw/master/screenshot/1.gif)

京东到家首页

![jingdong](https://github.com/leiyun1993/HIndicator/raw/master/screenshot/2.gif)

下面使我们自定义的效果

![zidingyi](https://github.com/leiyun1993/HIndicator/raw/master/screenshot/3.gif)

### 1、分析

其实这个很简单，主要就是有以下几点

>* 绘制一个圆角矩形做背景；
>* 绘制一个圆角矩形做指示器；
>* 确定指示器的长度和指示器的位置；
>* 根据RecyclerView滑动的距离动态改变指示器的位置。


### 2、绘制指示器

绘制背景的圆角矩形的时候，不考虑padding信息，就很简单

```kotlin
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        mBgRect.set(0f, 0f, w * 1f, h * 1f)
        mRadius = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制背景
        canvas?.drawRoundRect(mBgRect, mRadius, mRadius, mBgPaint)
    }
```

绘制指示器

**ratio**指的是指示器长度，即如果滚动内容有两屏，则指示器应该为1/2长度，以此类推
（当然上面所示app不一定实现了这个，可能会为了美观设置一个固定比例）

**progress**指的是滑动距离和指示器对应关系，这个实际上就是滑动进度条的意思

```kotlin
    //计算指示器的长度和位置
    val leftOffset = viewWidth * (1f - ratio) * progress
    val left = mBgRect.left + leftOffset
    val right = left + viewWidth * ratio
    mRect.set(left, mBgRect.top, right, mBgRect.bottom)

    //绘制指示器
    canvas?.drawRoundRect(mRect, mRadius, mRadius, mPaint)
```

### 3、和RecyclerView联动

获取RecyclerView滚动的位置可根据以下几个方法获取

>* computeVerticalScrollExtent()/computeHorizontalScrollExtent是当前屏幕显示的区域高度
>* computeVerticalScrollOffset()/computeHorizontalScrollOffset 是当前屏幕之前滑过的距离
>* computeVerticalScrollRange()/computeHorizontalScrollRange是整个RecycleView控件的高度

监听滑动，配合上诉方法就可以拿到滑动位置的比例

```kotlin
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val offsetX = recyclerView.computeHorizontalScrollOffset()
            val range = recyclerView.computeHorizontalScrollRange()
            val extend = recyclerView.computeHorizontalScrollExtent()
            val progress: Float = offsetX * 1.0f / (range - extend)     //因为指示器有长度，所以这里需要减去首屏长度
            this@HIndicator.progress = progress     //设置滚动距离所占比例
        }
    })
```