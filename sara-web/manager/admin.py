from django.contrib import admin
from .models import Institution
from .models import Campus
from .models import RequirementType
from .models import Requirement
from .models import Area
from .models import RoomType
from .models import Room

admin.site.register(Institution)
admin.site.register(Campus)
admin.site.register(RequirementType)
admin.site.register(Requirement)
admin.site.register(Area)
admin.site.register(RoomType)
admin.site.register(Room)
admin.site.register(Teacher)
admin.site.register(Program)
admin.site.register(Course)
