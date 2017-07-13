# sara-web
In progress...

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project in the live system.

### Prerequisites

What things you need to install the software and how to install them

```
Python 3.5 or superior

You need to install a DB(SqLite, PostgreSQL) of your choice to create the tables and to invoke services.
```

> You can see a short (and very good) tutorial about Python and Django [here](https://tutorial.djangogirls.org/).
  In the Django Girls tutorial you will find specific steps for your operating system.

### Installing

Create the python virtualenv

```
python3 -m venv myvenv
```

Active the virtualenv

```
source myvenv/bin/activate
```

Install project requirements save in requirements.txt

```
pip install -r requirements.txt
```

### Initialize Default Data
> This section is optional. Just run the commands bellow if you want load a default dataset.

Create the migration files by running:

```
python manage.py makemigrations
```

and after, apply them:

```
python manage.py makemigrations manager
```
and
```
python manage.py migrate
```

Run the command bellow to load initial data:

```
python manage.py loaddata manager/fixtures/initial_data.json
```

### Running

You'll need to create a user with higher permissions, called "super user". To create a super user, run:

```
python manage.py createsuperuser
```

Run the main python module

```
python manage.py runserver
```


## Authors

See also the list of [contributors](https://github.com/icarojerry/sara/contributors) who participated in this project.

## License

This project is licensed under the GPL-3.0 License - see the [LICENSE](LICENSE) file for details
