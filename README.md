# Xml2XmlTransformation ( XMLDocSorting )

_Hi_,
_I know it's tough when you are working with xml files, modifying it, removing some nodes, adding some nodes programmatically and end up having xml doc with improper & inconsistent pattern.
So why not just apply few lines of code with XSLT on an xml doc!!_

Well, I'm not good at **XSLT(Extensible Stylesheet Language Transformations)** but to be honest it's amazing when you do it :D

Move to the business, well I have an unordered `data.xml` file (don't judge it, I know its a nonsense data file but it doesn't matter what file you have when you don't know how to sort the entire doc :P) which needs to be sorted and make a new copy of the same in `data-new.xml`.

And I want to sort the data.xml file according to some use cases like :
  - I want `<Person>` node to be appear first and then `<work>` node should come ...etc.
  - Plus I want to sort the Person node in the doc by `<name>` & also want the `<email>(s)` tag to be sorted within it by text value. 
  - With the above cases said, I also want to tweak the sequence of work node by its location
  
__NOTE::__ 
  * You can notice that the xml contains namespaces also, so few namespaced tags (e.g. `<m:name>` )
  * The point to use namespace it to show we can transform an xml with many namespaces.
  * And you just have to do for the same is to mention namespace in the `<xsl:stylesheet>` tag as an attribute (have a look on my sample file)
  
  
  
### THANK YOU,
      &
   _GOOD LUCK_
