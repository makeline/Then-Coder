package org.aerovek.chartr.data.buildconfig

import org.aerovek.chartr.data.network.ElrondNetwork

interface EnvironmentRepository {
    val selectedElrondEnvironment: ElrondNetwork
    val selectedPrimeTrustEnvironme