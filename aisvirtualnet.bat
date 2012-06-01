@echo OFF
set CLASSPATH=.;lib/*;./*
@echo ON
java dk.dma.aisvirtualnet.AisVirtualNet %*
