# Alfresco remote JODConverter

This project aims at externalizing office documents conversions to a remote JODConverter server.

## Addon prerequisites

These are the prerequisites for this addon : You have a remote host with a JODConverter server on board. Or even better have a JODConverter farm behind a load balancer.

Compatible with our Docker Image `xenit/jodconverter` as of v4.2.3.
For the latest release, please visit https://github.com/xenit-eu/jodconverter or https://hub.docker.com/r/xenit/jodconverter/.

## Addon installation

This is how you should be installing this addon in production : Just use alfresco mmt for applying the amp to the alfresco war

## Addon configuration

This is the default global config for the addon :
```
# Set the prority of the transformer, lower number means higher priority
content.transformer.RemoteJODConverter.priority=30
# Endpoint for the jod converter, should either point to one server or to the load balancer
content.transformer.RemoteJODConverter.endpoint=http://jodconverter:8080/converter
# Timeout setting for the transformation 
content.transformer.RemoteJODConverter.timeoutMs=60000
# Timeout setting for reading the file
content.transformer.RemoteJODConverter.readLimitTimeMs=60000

```
Feel free to copy/paste that to your alfresco-global.properties and adjust these parameters as you see fit

This is the default config for extension based automatic transformations :

```
content.transformer.RemoteJODConverter.extensions.*.xlsm.supported=false
content.transformer.RemoteJODConverter.extensions.*.pptm.supported=false
content.transformer.RemoteJODConverter.extensions.*.sldm.supported=false
content.transformer.RemoteJODConverter.extensions.*.xltx.supported=false
content.transformer.RemoteJODConverter.extensions.*.docx.supported=false
content.transformer.RemoteJODConverter.extensions.*.potx.supported=false
content.transformer.RemoteJODConverter.extensions.*.xlsx.supported=false
content.transformer.RemoteJODConverter.extensions.*.pptx.supported=false
content.transformer.RemoteJODConverter.extensions.*.xlam.supported=false
content.transformer.RemoteJODConverter.extensions.*.docm.supported=false
content.transformer.RemoteJODConverter.extensions.*.xltm.supported=false
content.transformer.RemoteJODConverter.extensions.*.dotx.supported=false
content.transformer.RemoteJODConverter.extensions.*.xlsb.supported=false
content.transformer.RemoteJODConverter.extensions.*.sldx.supported=false
content.transformer.RemoteJODConverter.extensions.*.ppsm.supported=false
content.transformer.RemoteJODConverter.extensions.*.potm.supported=false
content.transformer.RemoteJODConverter.extensions.*.ppam.supported=false
content.transformer.RemoteJODConverter.extensions.*.dotm.supported=false
content.transformer.RemoteJODConverter.extensions.*.ppsx.supported=false
content.transformer.RemoteJODConverter.extensions.xlsm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.pptm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xls.pdf.maxSourceSizeKBytes=10240
content.transformer.RemoteJODConverter.extensions.sldm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xltx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.potx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.docx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xlsx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.pptx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xlam.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.ppt.pdf.maxSourceSizeKBytes=6144
content.transformer.RemoteJODConverter.extensions.docm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xltm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.dotx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xlsb.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.sldx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.ppsm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.potm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.txt.pdf.maxSourceSizeKBytes=5120
content.transformer.RemoteJODConverter.extensions.rtf.pdf.maxSourceSizeKBytes=5120
content.transformer.RemoteJODConverter.extensions.ppam.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.dotm.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.doc.pdf.maxSourceSizeKBytes=10240
content.transformer.RemoteJODConverter.extensions.vsd.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.vsdx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.ppsx.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.html.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.odp.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.odt.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.ods.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.odf.pdf.maxSourceSizeKBytes=4096
content.transformer.RemoteJODConverter.extensions.xlsm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.pptm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xls.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.sldm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xltx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.potx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.docx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xlsx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.pptx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xlam.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.ppt.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.docm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xltm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.dotx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xlsb.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.sldx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.ppsm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.potm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.txt.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.rtf.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.ppam.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.dotm.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.doc.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.vsd.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.vsdx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.ppsx.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.html.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.odt.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.odp.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.ods.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.odf.pdf.supported=true
content.transformer.RemoteJODConverter.extensions.xlsm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.pptm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.xls.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.sldm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.xltx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.potx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.docx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.xlsx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.pptx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.xlam.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.ppt.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.docm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.xltm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.dotx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.xlsb.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.sldx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.ppsm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.potm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.txt.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.rtf.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.ppam.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.dotm.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.doc.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.vsd.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.vsdx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.ppsx.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.html.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.odp.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.odt.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.ods.pdf.priority=30
content.transformer.RemoteJODConverter.extensions.odf.pdf.priority=30
```

You can add extra configuration for other document extensions as long as :
1) Its mimetype is supported by the transformer
2) The transformation is supported by the remote JOD Converter
3) Extra config enteries should look like this :

```
content.transformer.RemoteJODConverter.extensions.<src-ext>.<dst-ext>.supported=true
content.transformer.RemoteJODConverter.extensions.<src-ext>.<dst-ext>.priority=30
content.transformer.RemoteJODConverter.extensions.<src-ext>.<dst-ext>.maxSourceSizeKBytes=<max-filesize>
```

## Publishing
Currently only snapshots are published, without being signed.
