project(gcem)

include(CompileWarnings)

file(GLOB_RECURSE gcem_src src/main/native/cpp/*.cpp)

add_library(gcem ${gcem_src})
set_target_properties(gcem PROPERTIES DEBUG_POSTFIX "d")

set_property(TARGET gcem PROPERTY FOLDER "libraries")
target_compile_features(gcem PUBLIC cxx_std_23)

target_include_directories(
    gcem
    PUBLIC $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/src/main/native/include>
)

wpilib_target_warnings(gcem)

install(TARGETS gcem EXPORT gcem)
export(TARGETS gcem FILE gcem.cmake NAMESPACE gcem::)
