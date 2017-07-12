# -*- coding: utf-8 -*-
from django.db import models

class Institution(models.Model):
    id = models.IntegerField
    acronym = models.CharField(max_length=20, blank=False, null=False)
    name = models.CharField(max_length=200, blank=False, null=False)


    def __str__(self):
        return self.name

class Campus(models.Model):
    id = models.IntegerField
    name = models.CharField(max_length=200, blank=False, null=False)
    address = models.CharField(max_length=200, blank=False, null=False)
    institution = models.ForeignKey('Institution')

    def __str__(self):
        return self.name

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

class RoomType(models.Model):
    id = models.IntegerField
    name = models.CharField(max_length=50, blank=False, null=False)


    def __str__(self):
        return self.name

class Room(models.Model):
    id = models.IntegerField
    code = models.CharField(max_length=20, blank=False, null=False)
    description = models.CharField(max_length=100, blank=False, null=False)
    capacity = models.IntegerField
    type = models.ForeignKey('RoomType')
    area = models.ForeignKey('Area')

    def __str__(self):
        return self.description