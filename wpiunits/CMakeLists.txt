project(wpiunits)

include(SubDirList)
include(CompileWarnings)
include(AddTest)

# Java bindings
if(WITH_JAVA)
    include(UseJava)

    file(GLOB_RECURSE JAVA_SOURCES src/main/java/*.java src/generated/main/java/*.java)

    add_jar(
        wpiunits_jar
        ${JAVA_SOURCES}
        OUTPUT_NAME wpiunits
        OUTPUT_DIR ${WPILIB_BINARY_DIR}/${java_lib_dest}
    )
    set_property(TARGET wpiunits_jar PROPERTY FOLDER "java")

    install_jar(wpiunits_jar DESTINATION ${java_lib_dest})
    install_jar_exports(TARGETS wpiunits_jar FILE wpiunits.cmake DESTINATION share/wpiunits)
    install(FILES wpiunits-config.cmake DESTINATION share/wpiunits)
endif()

if(WITH_JAVA_SOURCE)
    include(UseJava)
    include(CreateSourceJar)
    add_source_jar(
        wpiunits_src_jar
        BASE_DIRECTORIES
            ${CMAKE_CURRENT_SOURCE_DIR}/src/main/java
            ${CMAKE_CURRENT_SOURCE_DIR}/src/generated/main/java
        OUTPUT_NAME wpiunits-sources
        OUTPUT_DIR ${WPILIB_BINARY_DIR}/${java_lib_dest}
    )
    set_property(TARGET wpiunits_src_jar PROPERTY FOLDER "java")

    install_jar(wpiunits_src_jar DESTINATION ${java_lib_dest})
endif()

add_library(wpiunits ${wpiunits_native_src})
set_target_properties(wpiunits PROPERTIES DEBUG_POSTFIX "d")
set_property(TARGET wpiunits PROPERTY FOLDER "libraries")

target_compile_features(wpiunits PUBLIC cxx_std_23)
wpilib_target_warnings(wpiunits)
target_link_libraries(wpiunits gcem)

target_include_directories(
    wpiunits
    PUBLIC
        $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/src/main/native/include>
)

install(TARGETS wpiunits EXPORT wpiunits)
export(TARGETS wpiunits FILE wpiunits.cmake NAMESPACE wpiunits::)
install(DIRECTORY src/main/native/include/ DESTINATION "${include_dest}/wpiunits")

configure_file(wpiunits-config.cmake.in ${WPILIB_BINARY_DIR}/wpiunits-config.cmake)
install(FILES ${WPILIB_BINARY_DIR}/wpiunits-config.cmake DESTINATION share/wpiunits)
install(EXPORT wpiunits DESTINATION share/wpiunits)

if(WITH_TESTS)
    wpilib_add_test(wpiunits src/test/native/cpp)
    target_include_directories(wpiunits_test PRIVATE src/test/native/include)
    target_link_libraries(wpiunits_test wpiunits googletest)
endif()


