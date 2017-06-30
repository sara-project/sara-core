from django.contrib import admin
from .models import RequirementType
from .models import Requirement

admin.site.register(RequirementType)
admin.site.register(Requirement)
