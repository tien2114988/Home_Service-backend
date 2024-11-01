# Home Service API
## Run project
- ./mvnw clean install

## Git
### Branch
- main : for production (**do not be allowed push to this branch**)
- dev : for dev environment (**do not be allowed push to this branch**)
- fix/... : fix something (**merge branch dev to this branch before push**) (**example**: git commit -m 'fix: ui': fix/bank-account)
- feature/... : develop feature (**merge branch dev to this branch before push**) (**example**: fix/house-cleaning-service)
- refactor/... : refactor something (**merge branch dev to this branch before push**)

### Commit Convention
- feature: something (**example**: git commit -m 'feature: add house cleaning service')
- fix: something (**example**: git commit -m 'fix: ui')
- refactor: something 
- merge: something (**example**: git commit -m 'merge: branch "dev" to branch "fix/bank-account"')

## RedirectUri, Webhook
- RedirectUri BankHub : ```homeService://BankLink```
- PayOs Webhook Url : ```your_domain/api/payOs```
- BankHub Grant Webhook Url : ```your_domain/api/bankHub```

