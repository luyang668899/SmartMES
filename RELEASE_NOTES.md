# Qcadoo MES Release Notes

## Version 1.5.0

### New Features and Enhancements

1. **Supply Chain Management Plugin**
   - Supplier management and rating
   - Purchase request and order management
   - Procurement optimization

2. **Data Analysis Plugin**
   - Dashboard creation and management
   - KPI metrics tracking and analysis
   - Report templates and generation

3. **System Integration Plugin**
   - ERP/CRM integration connections
   - Data mapping and transformation
   - Integration logging and monitoring

4. **Advanced Planning Plugin**
   - Production plan optimization using genetic algorithms
   - Scheduling constraints management
   - Resource allocation and utilization

5. **Digital Twin Plugin**
   - Digital twin model management
   - Production simulation scenarios
   - What-if analysis and recommendations

### Technical Improvements

- **Cross-platform support** for Windows, Linux, and macOS
- **Enhanced build scripts** with dependency fix options
- **Improved release packaging** including all necessary files
- **Maven-based build system** with comprehensive module support

### How to Build and Package for Release

1. **Using Linux/macOS build script**:
   ```bash
   ./build.sh
   # Select option 3: Build and package for release
   # Enter release version (e.g., 1.5.0)
   ```

2. **Using Windows build script**:
   ```cmd
   build.bat
   # Select option 3: Build and package for release
   # Enter release version (e.g., 1.5.0)
   ```

3. **Release package contents**:
   - mes-application.war (main application)
   - README.md (project documentation)
   - LICENSE.txt (AGPL license)
   - build.sh (Linux/macOS build script)
   - build.bat (Windows build script)

### Known Issues

- **Java version compatibility**: Requires Java 8 for optimal build performance
- **AspectJ dependency**: May require fresh Maven repository for aspectjweaver
- **Build environment**: tools.jar dependency may need special handling in Java 9+

### Troubleshooting

- **Dependency issues**: Use build script option 5 to rebuild with fresh dependencies
- **Java version**: Ensure Java 8 is installed and properly configured
- **Maven repository**: Clean ~/.m2/repository/org/aspectj/ if encountering aspectjweaver errors

### Support

For questions and support, please refer to the project documentation or contact the development team.

---

© 2026 Qcadoo MES Team
Licensed under GNU AGPLv3
