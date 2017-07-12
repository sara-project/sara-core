from django.db import models

class RequirementType(models.Model):
    id = models.IntegerField
    name = models.CharField(max_length=50, blank=False, null=False)


    def __str__(self):
        return self.name

class Requirement(models.Model):
    id = models.IntegerField
    type = models.ForeignKey('RequirementType')
    description = models.CharField(max_length=100, blank=False, null=False)


    def __str__(self):
        return self.description

class Area(models.Model):
    id = models.IntegerField
    code = models.CharField(max_length=20, blank=False, null=False)
	description = models.CharField(max_length=100, blank=False, null=False)

    def __str__(self):
        return self.name

class RoomtType(models.Model):
    id = models.IntegerField
    name = models.CharField(max_length=50, blank=False, null=False)


    def __str__(self):
        return self.name

class Room(models.Model):
    id = models.IntegerField
    code = models.CharField(max_length=20, blank=False, null=False)
    description = models.CharField(max_length=100, blank=False, null=False)
	capacity = models.IntegerField
	type = models.ForeignKey('RoomtType')
	area = models.ForeignKey('Area')

    def __str__(self):
        return self.description