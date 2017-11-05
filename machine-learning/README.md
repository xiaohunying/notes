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

### One-vs-All (One-vs-Rest)

multi-class

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
- Conjugate Gradient (**Pros:** no need to pick $\alpha$, fast. **Cons:** complex)
- BFGS (**Pros:** no need to pick $\alpha$, fast. **Cons:** complex)
- L-BFGS (**Pros:** no need to pick $\alpha$, fast. **Cons:** complex)
- Normal Equation (Too expensive: need to calculate inverse of matrix)

## Gradient Descent

Repeat until convergence: &nbsp; $\theta_{j}:=\theta_{j}-\alpha\frac{\partial}{\partial \theta_{j}}J(\theta)$

Where $j$ represents the feature index number. $\alpha$ is called **Learning Rate**.

- Batch Gradient Descent: uses all the training data at one time



# Others

- Feature Scaling
- Mean Normalization
