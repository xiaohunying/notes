# Supervised Learning - Regression Problem

## Linear Regression

Hypothesis Function: &nbsp; $h_{\theta}(x)=\theta_{0}+\theta_{1}x$

Parameters: &nbsp; $\theta_{0}$, $\theta_{1}$

Cost Function: &nbsp; $J(\theta)=\frac{1}{2m}\sum_{i=1}^{m}(h_{\theta}(x^{(i)})-y^{i})^{2}$

Goal: &nbsp; $\underset{\theta}{minimize}~J(\theta)$

## Polynomial Regression

Hypothesis Function: &nbsp; $h_{\theta}(x)=\theta_{0}+\theta_{1}x_{1}+\theta_{2}x_{1}^{2}~~\Rightarrow ~~h_{\theta}(x)=\theta_{0}+\theta_{1}x_{1}+\theta_{2}x_{2}$ &nbsp;&nbsp; (where $x_{2}=x_{1}^{2}$)

Hypothesis Function: &nbsp; $h_{\theta}(x)=\theta_{0}+\theta_{1}x_{1}+\theta_{2}\sqrt{x_{1}}~~\Rightarrow ~~h_{\theta}(x)=\theta_{0}+\theta_{1}x_{1}+\theta_{2}x_{2}$ &nbsp;&nbsp; (where $x_{2}=\sqrt{x_{1}}$)

# Supervised Learning - Classification Problem

## Logistic Regression

Hypothesis representation is **Sigmoid Function** which is also called **Logistic Function**.

Hypothesis Function: &nbsp; $h_{\theta }(x)=\frac{1}{1+e^{-\theta^{T}x}}$

The decision boundary is the line that separates the area where $y=0$ and $y=1$. It is created by the hypothesis function. For logistic function, the decision boundary is $\theta^{T}x=0$.

cost Function: &nbsp; $cost(h_{\theta}(x),y)=-y~log(h_{\theta}(x))-(1-y)~log(1-h_{\theta}(x))$ &nbsp;&nbsp;&nbsp;&nbsp; ($y=0$ or $y=1$)

cost Function: &nbsp; $J(\theta)=-\frac{1}{m}\sum_{i=1}^{m}[y^{(i)}log(h_{\theta}(x^{i}))+(1-y^{i})log(1-h_{\theta}(x^{i}))]$

## Multiclass Classification

Use **One-vs-All** (**One-vs-Rest**)

$y\in \{ 0, 1,  ... , n \}$

$h_{\theta}^{(0)}(x)=P(y=0|x; \theta)$

$\vdots$

$h_{\theta}^{(n)}(x)=P(y=n|x; \theta)$

$prediction=\underset{i}{max}(h_{\theta}^{(i)}(x))$


## SVM: Support Vector Machine Hypothesis

## Neural Network

- activation units
- bias nodes x_{0}



# Unsupervised Learning

## Clustering

### K-Means



# PCA: Principal Component Analysis

Reduce data from n-dimensions to k-dimensions

## Application: Dimensionality Reduction

- Data Compression
- Data Visualization

## [u, s, v] = svd(sigma)

\[U_{reduce} = u(:, 1:k)\]

Z = U_{reduce}' * x; (~~x_{0}=1~~)



# Optimization Algorithms

- Gradient Descent
- Conjugate Gradient - **Pros:** no need to pick $\alpha$, fast. **Cons:** complex
- BFGS -- **Pros:** no need to pick $\alpha$, fast. **Cons:** complex
- L-BFGS (**Pros:** no need to pick $\alpha$, fast. **Cons:** complex)
- Normal Equation (Too expensive: need to calculate inverse of matrix)

## Gradient Descent

Repeat until convergence: &nbsp; $\theta_{j}:=\theta_{j}-\alpha\frac{\partial}{\partial \theta_{j}}J(\theta)$

Where $j$ represents the feature index number. $\alpha$ is called **Learning Rate**.

- Batch Gradient Descent: uses all the training data at one time



# Others

- Feature Scaling
- Mean Normalization
- Regularization: address overfitting (Regularization Parameter $\lambda$)
- Convex Function: bowl-shaped function
- Contour plot
