回归算法:
	线性回归算法
	逻辑回归算法
	保序回归算法
分类算法:
	贝叶斯分类算法
	SVM支持向量机算法
	决策树算法
聚类算法:
	KMeans聚类算法
	LDA主题模型算法
关联规则挖掘算法:
	FPGrowth关联规则算法
推荐算法:
	ALS交替最小二乘算法
	协同过滤推荐算法
神经网络算法:
	神经网络算法综述





数据类型:
Vector:一个数学向量,支持稠密向量和稀疏微量.前者表示向量的每一位都存储下来,后者则只存储非零位以节约空间[只有10%元素为非零元素使用,降低内存提高速度].
LabeledPoint:表示带标签的数据点。包含一个特征向量与一个标签（由一个符点数表示）
Rating:用户对一个产品的评分,在mllib.recommendation包中,用于产品推荐.
各种Model类:每个Model都是训练算法的结果,一般有一个predict()方法可以用来对新的数据点或数据点组成的RDD应用该模型进行预测.



操作向量:
import org.apache.spark.mllib.linalg.Vectors
//创建稠密向量<1.0,2.0,3.0>;Vectors.dense接收一串值或一个数组
val denseVec1: Vector = Vectors.dense(1.0,2.0,3.0)
val denseVec2: Vector = Vectors.dense(Array(1.0,2.0,3.0))
//创建稀疏向量<1.0,0.0,2.0,0.0>;该方法只接收微量的维度(这里是4)以及非零位的位置和对应的值
val sparseVec1: Vecto4r = Vectors.sparse(4,Array(0,2),Array(1.0,2.0))



算法:
特征提取:
TF-IDF(词频-逆文档频率)
1.缩放
2.正规化
3.Word2Vec



统计:(通过mllib.stat.Statistics类中的方法提供了几种广泛使用的统计函数)
1.Statistics.colStats(rdd)
2.Statistics.corr(rdd,method)
3.Statistics.corr(rdd1,rdd2,method)
4.Statistics.chiSqTest(rdd)





过拟合原因:
	拟合了过多的特征.
解决方法:
	1.减少特征数量(减少牲会推动一些信息,即使特征选得很好).
	可用人工选择需要保留的特征,或者采用模型选择算法选取特征.
	2.正则化(特征较多时比较有效).


回归算法:
如果是连续的，就是多重线性回归；
如果是二项分布，就是Logistic回归；
如果是Poisson(泊松)分布，就是Poisson回归；
如果是负二项分布，就是负二项回归。

线性回归算法:
在训练模型时是建议采用正则化手段的，特别是在训练数据的量特别少的时候，
若不采用正则化手段，过拟合现象会非常严重。
L2正则化相比L1而言会更容易收敛（迭代次数少），
但L1可以解决训练数据量小于维度的问题（也就是n元一次方程只有不到n个表达式，这种情况下是多解或无穷解的）。


逻辑回归算法:
在实际应用的场景中，L-BFGS比SGD更容易收敛，效果更好一些，推荐大家用L-BFGS。

贝叶斯定理:
p(B|A)=(p(A|B)p(B))/p(A)=p(AB)/p(A)
p(A|B)=p(AB)/p(B)