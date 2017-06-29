from django.db import models

class RequirementType(models.Model):
    id = models.IntegerField
    name = models.CharField(max_length=50)


    def __str__(self):
        return self.name
