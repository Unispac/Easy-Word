# Easy-Word
![1563334271718](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/0.png)

Easy-Word是一款用Java开发的便捷式单词记忆辅助软件。

集成了“单词查询”，“记忆曲线法”，“笔记批注”，“批量导入”等所有常见功能。主要解决了“金山词霸”这类软件无法附加笔记批注，不支持批量导入等问题。

（使用便捷，界面友好。[其实主要是比较符合我个人记忆习惯。。。不保证所有人都能获得优秀体验。。]）



### 创建生词本

![1563335647424](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/6.png)

![1563335667677](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/7.png)

![1563335679989](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/8.png)



### 单词查询与导入

![1563334673510](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/1.png)

Easy-Word内嵌浏览器，通过html页面的形式，使用必应词典，提供单词查询功能。可以得到可靠的中文词意，样例图片，例句等。Easy-Word会自动抓取用户在浏览器中搜索的单词，选择单词本，做好笔记，点击Insert即可加入目标单词本中。**所有单词本都会自动过滤重复的单词**



### 批量导入

![1563334987182](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/2.png)

Easy-Word支持格式化批量导入功能。**你可以在文本编辑器里**（推荐使用windows写字板编辑，千万不要用word这种二进制格式）**以上图所示的 " \* + 单词 + 笔记注释 + \*" 的格式记录单词**（\*应该单独成行，单词应该出现在第一行，注释跟在单词后面，可以是多行），**并将文本以.words后缀命名**。

点击主页的“Import New Word”按钮即可一键导入。

![1563335443589](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/3.png)

![1563335567887](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/4.png)

![1563335595738](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/5.png)



### 艾宾浩斯背单词

![1563336031358](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/14.png)

Easy-Word采用记忆曲线法帮助用户背单词。

加入单词本中的单词可以通过点击Memorize按钮查看和记忆。

![1563335791618](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/10.png)

![1563335830877](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/11.png)

选中单词本，点击START 即可开始。

![1563335869408](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/12.png)

根据对单词的熟悉程度，点击认识/不认识。Easy-Word会结合记忆曲线和用户的熟悉度决定下一次的记忆时间。

![1563335949954](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/13.png)

用户反馈后，内嵌的浏览器会自动帮用户跳转到单词对应的页面，也会将用户的笔记呈现出来，用户可以自行查看并强化记忆。右边，Easy-Word会给出下一次记忆的时间和用户对这个单词的记忆准确率。

用户可以点击Delete From List按钮将单词从单词本中移除，也可以点击Next按钮背诵下一个单词。点击Home按钮，则会回到主页。



### 复习

![1563336158671](https://raw.githubusercontent.com/Unispac/Easy-Word/master/img/15.png)

生词本中的单词经过一个完整的记忆曲线周期后，会被加入复习本中，不会再在memorize页面中出现，用户可以点击Review按钮复习这些单词，复习过程中，用户忘记的单词又会背重新加入生词本。