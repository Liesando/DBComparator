package com.otoil.dbcomparator.shared.services;


import com.otoil.dbcomparator.shared.beans.DatabaseNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonBean
{
    private DatabaseNode source;
    private DatabaseNode dest;
}